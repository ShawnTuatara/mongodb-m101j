package com.tengen.m101j.week2;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Component;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

@Component
public class Week2Homework2 implements CommandLineRunner {
	private static Logger log = LoggerFactory.getLogger(Week2Homework2.class);

	@Autowired
	Mongo mongo;

	public void run(String... args) {
		DBCollection collection = mongo.getDB("students").getCollection("grades");

		int previousStudentId = -1;
		DBCursor cursor = collection.find(new BasicDBObject("type", "homework")).sort(new BasicDBObject("student_id", 1).append("score", 1));
		for (DBObject dbObject : cursor) {
			int currentStudentId = (int) dbObject.get("student_id");
			if (currentStudentId != previousStudentId) {
				log.info("Removing: {}", dbObject);
				collection.remove(dbObject);
			}

			previousStudentId = currentStudentId;
		}

		AggregationOutput output = collection.aggregate((DBObject) JSON.parse("{'$group':{'_id':'$student_id', 'average':{$avg:'$score'}}}"),
				(DBObject) JSON.parse("{'$sort':{'average':-1}}"), (DBObject) JSON.parse("{'$limit':1}"));

		DBObject result = output.results().iterator().next();
		log.info("The student id is: {}", result.get("_id"));
	}

	@Autowired
	MongoTemplate mongoTemplate;

	// rename to "run" and rename other method to "runMongo"
	public void runTemplate(String... args) {
		List<Score> scores = mongoTemplate.find(query(where("type").is("homework")).with(new Sort("student_id", "score")), Score.class, "grades");

		int previousStudentId = -1;
		for (Score score : scores) {
			if (score.getStudentId() != previousStudentId) {
				log.info("Removing: {}", score);
				mongoTemplate.remove(score, "grades");
			}
			previousStudentId = score.getStudentId();
		}

		Aggregation aggregation = newAggregation(group("student_id").avg("score").as("average"), sort(Direction.DESC, "average"), limit(1));
		AggregationResults<StudentAverageScore> results = mongoTemplate.aggregate(aggregation, "grades", StudentAverageScore.class);
		log.info("The student id is: {}", results.getUniqueMappedResult().getId());
	}
}