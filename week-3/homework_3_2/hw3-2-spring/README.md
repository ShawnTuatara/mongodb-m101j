M101J - Week 3 - Homework 3-2 / 3-3

# Notes
Took the solution from hw2-3-spring as a starting point for this homework. Split out the @RequestMapping's into 3 controllers. As I am working with a MongoRepository it was straight forward to just add the comment to the existing document instead of trying to use the $push operation.

# Included Spring Dependencies
- Spring Boot Starter Web
- Spring Boot Starter Data MongoDB
- Spring Boot Starter Thymeleaf
- Spring Boot Starter FreeMarker
- Hibernate Validator

# Configuration
For most cases using the default configuration is all you need as such you do not need an `application.yml` file. If you installed mongod somewhere other than your local machine on the default port or you want to run the web server on a different port then you will want to include a `application.yml` file at the same directory as the jar file. The sample file below shows the defaults.

```yaml
spring:
  data:
    mongodb:
      host: 127.0.0.1
      port: 27017
      database: blog
server:
  port: 8080
```

# Running Homework
## With Maven
### Dependencies
- Java 7
- Maven 3

### Steps
1. Clone repository (git clone https://github.com/ShawnTuatara/mongodb-m101j.git)
1. Open a command window / shell and navigate to the `mongodb-m101j/week-3/homework_3_2/hw3-2-spring` directory (Where this README.md is located)
1. Run `mvn clean package` to build the required jar file
1. Navigate to the `target` directory and run `java -jar hw3-2-spring-1.0-SNAPSHOT.jar`
  1. Optionally create an `application.yml` file in the `target` directory based on the example in `src/main/resources/application.yml`

## Using distrib
### Dependencies
- Java 7

### Steps
1. Download the jar file from the [distrib](https://github.com/ShawnTuatara/mongodb-m101j/tree/master/week-3/homework_3_2/hw3-2-spring/distrib) directory
  1. Optionally download the `appliation.yml` file from the `distrib` directory and configure the location of the mongod instance if you have installed it somewhere other than the local machine and default port
1. Open a command window / shell and run the command `java -jar hw3-2-spring-1.0-SNAPSHOT.jar`