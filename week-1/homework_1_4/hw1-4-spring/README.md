M101J - Week 1 - Homework 1-4

# Included Spring Dependencies
- Spring Boot Starter Web
- Spring Boot Starter Data MongoDB
- Spring Boot Starter Thymeleaf

# Configuration
- Create an application.yml file in the same directory as the jar.

```yaml
spring:
  data:
    mongodb:
      host: 127.0.0.1
      port: 27017
```

# Running Homework
## With Maven
### Dependencies
- Java 7
- Maven 3

### Steps
1. Clone repository (git clone https://github.com/ShawnTuatara/mongodb-m101j.git)
1. Open a command window / shell and navigate to the `mongodb-m101j/week-1/homework_1_4/hw1-4-spring` directory (Where this README.md is located)
1. Run `mvn clean package` to build the required jar file
1. Navigate to the `target` directory and run `java -jar h1-4-spring-1.0-SNAPSHOT.jar`
  1. Optionally create an `application.yml` file in the `target` directory based on the example in `src/main/resources/application.yml`

## Using distrib
### Dependencies
- Java 7

### Steps
1. Download the jar file from the [distrib](https://github.com/ShawnTuatara/mongodb-m101j/tree/master/week-1/homework_1_4/hw1-4-spring/distrib) directory
  1. Optionally download the `appliation.yml` file from the `distrib` directory and configure the location of the mongod instance if you have installed it somewhere other than the local machine and default port
1. Open a command window / shell and run the command `java -jar h1-4-spring-1.0-SNAPSHOT.jar`