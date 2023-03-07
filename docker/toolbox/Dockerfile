FROM openjdk:17 as build

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src src

RUN ./mvnw -B package


FROM openjdk:17

COPY --from=build target/telegramBot-0.0.1-SNAPSHOT.jar telegramBot-0.0.1.jar

ENTRYPOINT ["java","-jar","/telegramBot-0.0.1.jar"]