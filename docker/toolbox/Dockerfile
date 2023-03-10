
FROM maven:latest AS build
COPY . .
RUN mvn -f pom.xml clean package




FROM openjdk:17

COPY --from=build target/telegramBot-0.0.1-SNAPSHOT.jar telegramBot-0.0.1.jar

ENTRYPOINT ["java","-jar","/telegramBot-0.0.1.jar"]