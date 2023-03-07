FROM openjdk:17 as build

RUN ./mvnw -B package


FROM openjdk:17

COPY --from=build target/telegramBot-0.0.1-SNAPSHOT.jar telegramBot-0.0.1.jar

ENTRYPOINT ["java","-jar","/telegramBot-0.0.1.jar"]