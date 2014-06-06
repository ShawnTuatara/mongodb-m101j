M101J - Week 1 - Homework 1-4

# Included Spring Dependencies
- Spring Boot Starter Web
- Spring Boot Starter Data MongoDB
- Spring Boot Starter Thymeleaf

# Configuration
For most cases using the default configuration is all you need as such you do not need an `application.yml` file. If you installed mongod somewhere other than your local machine on the default port or you want to run the web server on a different port then you will want to include a `application.yml` file at the same directory as the jar file. The sample file below shows the defaults.

```yaml
spring:
  data:
    mongodb:
      host: 127.0.0.1
      port: 27017
server:
  port: 8080
```

# Running Homework
## Dependencies
- Java 7
- Maven 3

## Maven exec:java
1. Clone repository (git clone https://github.com/ShawnTuatara/mongodb-m101j.git)
1. Open a command window / shell and navigate to the `mongodb-m101j/week-1/homework_1_4/hw1-4-spring` directory (Where this README.md is located)
1. Run `mvn clean compile exec:java` to run the application
  1. Optionally modify the `src/main/resources/application.yml` file, before running the command, if you installed mongod to another machine or port
1. Navigate to [http://localhost:8080/](http://localhost:8080/) to view the answer

## Self-Executing JAR
1. Clone repository (git clone https://github.com/ShawnTuatara/mongodb-m101j.git)
1. Open a command window / shell and navigate to the `mongodb-m101j/week-1/homework_1_4/hw1-4-spring` directory (Where this README.md is located)
1. Run `mvn clean package` to build the required jar file
1. Navigate to the `target` directory and run `java -jar hw1-4-spring-1.0-SNAPSHOT.jar`
  1. Optionally create an `application.yml` file in the `target` directory based on the example in `src/main/resources/application.yml`
1. Navigate to [http://localhost:8080/](http://localhost:8080/) to view the answer
