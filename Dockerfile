# Docker multi-stage build

# 1. Building the App with Maven
FROM maven:3-jdk-11

ADD . /challengeone
WORKDIR /challengeone

# Just echo so we can see, if everything is there :)
RUN ls -l

# Run Maven build
RUN mvn -q clean package -Dmaven.test.skip -Dorg.slf4j.simpleLogger.defaultLogLevel=info

WORKDIR /app
COPY /challengeone/data/deniro.csv deniro.csv
COPY /challengeone/data/chinook.db chinook.db
#COPY /challengeone/target/app.jar app.jar
COPY --from=0 "/challengeone/target/app.jar" app.jar

FROM openjdk:12-alpine
MAINTAINER Tomas Pascual
RUN apk add bash

RUN addgroup -S spring && adduser -S spring -G spring
RUN chown -R spring:spring /app
RUN chmod 755 /app
USER spring:spring

EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.awt.headless=true", "-jar", "/app/app.jar", "--server.port=8080"]