
FROM maven:latest AS build
COPY pom.xml pom.xml

RUN mvn clean package




FROM openjdk:17

COPY --from=build target/telegramBot-0.0.1-SNAPSHOT.jar telegramBot-0.0.1.jar

ENTRYPOINT ["java","-jar","/telegramBot-0.0.1.jar"]
