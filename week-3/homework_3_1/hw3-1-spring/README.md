M101J - Week 3 - Homework 1

# Notes
There are two implementations of this homework assignment. One using the Mongo class directly and another using the MongoTemplate. Switch the naming of the `run()` method in `Week2Homework2.java` file if you want to try one or the other. If you are re-running the application you must re-import the data. Easiest way to do that is to drop the students collection in the mongo shell with `db.students.drop()` and then running the mongoimport again.

The Mongo class implementation used an $unwind and sort to get a similar list as week 2 homework 2 (hw2-2). The method then created a List of score documents that did not include the lowest homework score. Once the list was created the $set operator was used to update the student document with the new list of scores.

The MongoTemplate implementation worked more with model objects. A Student object that contained a List of Score objects. The Score object was almost the same as the previous homework assignment without the _id and student_id. A findAll() returned all the students from the collection. From that point it was just a matter of looping through the scores of each student to find the lowest homework Score and then remove it from the List. Once the Score was removed we could do a save() to put the document back into the collection.

# Included Spring Dependencies
- Spring Boot Starter Data MongoDB

# Configuration
For most cases using the default configuration is all you need as such you do not need an `application.yml` file. If you installed mongod somewhere other than your local machine on the default port then you will want to include a `application.yml` file at the same directory as the jar file. The sample file below shows the defaults.

```yaml
spring:
  data:
    mongodb:
      host: 127.0.0.1
      port: 27017
      database: school
```

# Running Homework
## Dependencies
- Java 7
- Maven 3

## Maven exec:java
1. Clone repository (git clone https://github.com/ShawnTuatara/mongodb-m101j.git)
1. Open a command window / shell and navigate to the `mongodb-m101j/week-3/homework_3_1/hw3-1-spring` directory (Where this README.md is located)
1. Run `mvn clean compile exec:java` to run the application
  1. Optionally modify the `src/main/resources/application.yml` file, before running the command, if you installed mongod to another machine or port
1. Look for the line `INFO  c.tengen.m101j.week3.Week3Homework1 - The student id is: ` for the answer

## Self-Executing JAR
1. Clone repository (git clone https://github.com/ShawnTuatara/mongodb-m101j.git)
1. Open a command window / shell and navigate to the `mongodb-m101j/week-3/homework_3_1/hw3-1-spring` directory (Where this README.md is located)
1. Run `mvn clean package` to build the required jar file
1. Navigate to the `target` directory and run `java -jar hw3-1-spring-1.0-SNAPSHOT.jar`
  1. Optionally create an `application.yml` file in the `target` directory based on the example in `src/main/resources/application.yml`
1. Look for the line `INFO  c.tengen.m101j.week3.Week3Homework1 - The student id is: ` for the answer