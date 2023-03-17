FROM maven:alpine as build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN mvn package




FROM openjdk:17

COPY --from=build /usr/app/target/telegramBot-0.0.1-SNAPSHOT.jar telegramBot-0.0.1.jar

ENTRYPOINT ["java","-jar","/telegramBot-0.0.1.jar"]

#FROM alpine
#RUN apk add --no-cache curl wget busybox-extras netcat-openbsd python py-pip $$ \
#    pip install awscli \
#RUN apk --purge -v del py-pip
#CMD tail -f /dev/null