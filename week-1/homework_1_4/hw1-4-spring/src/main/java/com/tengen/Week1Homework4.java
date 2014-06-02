package com.tengen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@Controller
public class Week1Homework4 {
	private static final Logger logger = LoggerFactory.getLogger("logger");

	@Autowired
	private Mongo mongoClient;

	@RequestMapping
	public String root(Model model) {
        DB database = mongoClient.getDB("m101");
        final DBCollection collection = database.getCollection("funnynumbers");
		
        // Not necessary yet to understand this.  It's just to prove that you
        // are able to run a command on a mongod server
        AggregationOutput output =
                collection.aggregate(
                        new BasicDBObject("$group",
                                new BasicDBObject("_id", "$value")
                                        .append("count", new BasicDBObject("$sum", 1)))
                        ,
                        new BasicDBObject("$match", new BasicDBObject("count",
                                new BasicDBObject("$lte", 2))),
                        new BasicDBObject("$sort", new BasicDBObject("_id", 1))
                );

        int answer = 0;
        for (DBObject doc : output.results()) {
            answer += (Double) doc.get("_id");
        }

        model.addAttribute("answer", answer);
        
		return "/answer";
	}

	public static void main(String[] args) {
		SpringApplication.run(Week1Homework4.class, args);
	}
}
