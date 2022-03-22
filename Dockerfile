# Docker multi-stage build (SPECIFICALLY BUILT FOR HEROKU DEPLOYMENT)

# 1. Building the App with Maven
FROM maven:3-jdk-11

ADD . /challengeone
WORKDIR /challengeone

# Just echo so we can see, if everything is there :)
RUN ls -l

# Run Maven build
RUN mvn -q clean package -Dmaven.test.skip

RUN ls -l ./target

WORKDIR /app
RUN ls -l /challengeone/data
RUN cp /challengeone/data/deniro.csv /app/deniro.csv
RUN cp /challengeone/data/chinook.db /app/chinook.db
RUN cp /challengeone/target/app.jar /app/app.jar

FROM openjdk:12-alpine
MAINTAINER Tomas Pascual
RUN apk add bash
RUN apk add curl

#RUN addgroup -S spring && adduser -S spring -G spring
#RUN chown -R spring:spring .
#RUN chmod 755 .
#USER spring:spring

RUN adduser -D myuser
USER myuser

EXPOSE 8080

RUN echo $JAVA_OPTS
RUN echo $PORT

CMD java -Dserver.port=$PORT $JAVA_OPTS -jar app.jars