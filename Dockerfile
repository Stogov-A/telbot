FROM maven:alpine as build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN mvn package




FROM openjdk:11

COPY --from=build target/telegramBot-0.0.1-SNAPSHOT.jar telegramBot-0.0.1.jar

ENTRYPOINT ["java","-jar","/telegramBot-0.0.1.jar"]
