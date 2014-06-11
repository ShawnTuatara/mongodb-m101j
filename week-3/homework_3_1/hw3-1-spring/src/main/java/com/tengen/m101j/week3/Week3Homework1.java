package com.tengen.m101j.week3;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Component;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

@Component
public class Week3Homework1 implements CommandLineRunner {
	private static Logger log = LoggerFactory.getLogger(Week3Homework1.class);

	@Autowired
	Mongo mongo;

	public void run(String... args) {
		DBCollection collection = mongo.getDB("school").getCollection("students");

		AggregationOutput input = collection.aggregate((DBObject) JSON.parse("{'$project': {scores: 1}}"), (DBObject) JSON.parse("{'$unwind': '$scores'}"),
				(DBObject) JSON.parse("{'$sort':{'_id': 1, 'scores.type': 1, 'scores.score': 1}}"));

		List<DBObject> updatedScores = new ArrayList<DBObject>();
		int previousStudentId = -1;
		boolean foundHomework = false;

		for (DBObject dbObject : input.results()) {
			int currentStudentId = (int) dbObject.get("_id");
			if (currentStudentId != previousStudentId && previousStudentId != -1) {
				updateScoresForStudent(collection, previousStudentId, updatedScores);
				updatedScores = new ArrayList<DBObject>();
				foundHomework = false;
			}

			previousStudentId = currentStudentId;
			DBObject currentScore = (DBObject) dbObject.get("scores");
			if (currentScore.get("type").equals("homework") && !foundHomework) {
				foundHomework = true;
				continue;
			}
			updatedScores.add(currentScore);
		}
		updateScoresForStudent(collection, previousStudentId, updatedScores);

		AggregationOutput output = collection.aggregate(new BasicDBObject("$unwind", "$scores"),
				(DBObject) JSON.parse("{'$group':{'_id':'$_id', 'average':{$avg:'$scores.score'}}}"), (DBObject) JSON.parse("{'$sort':{'average':-1}}"),
				(DBObject) JSON.parse("{'$limit':1}"));

		DBObject result = output.results().iterator().next();
		log.info("The student id is: {}", result.get("_id"));
	}

	private void updateScoresForStudent(DBCollection collection, int studentId, List<DBObject> updatedScores) {
		DBObject setOperator = new BasicDBObjectBuilder().push("$set").add("scores", updatedScores).get();
		collection.update(new BasicDBObject("_id", studentId), setOperator);
	}

	@Autowired
	MongoTemplate mongoTemplate;

	// rename to "run" and rename other method to "runMongo"
	public void runTemplate(String... args) {
		List<Student> students = mongoTemplate.findAll(Student.class);

		for (Student student : students) {
			Score minHomeworkScore = null;
			for (Score score : student.getScores()) {
				if ("homework".equals(score.getType()) && (minHomeworkScore == null || score.getScore() < minHomeworkScore.getScore())) {
					minHomeworkScore = score;
				}
			}

			student.getScores().remove(minHomeworkScore);
			mongoTemplate.save(student);
		}

		Aggregation aggregation = newAggregation(unwind("scores"), group("_id").avg("scores.score").as("average"), sort(Direction.DESC, "average"), limit(1));
		AggregationResults<StudentAverageScore> results = mongoTemplate.aggregate(aggregation, "students", StudentAverageScore.class);
		log.info("The student id is: {}", results.getUniqueMappedResult().getId());
	}
}