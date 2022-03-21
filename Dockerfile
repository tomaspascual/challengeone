FROM openjdk:12-alpine

MAINTAINER Tomas Pascual

RUN apk add bash

WORKDIR /app

RUN ls -l

RUN mvn clean install

COPY data/deniro.csv deniro.csv
COPY data/chinook.db chinook.db
COPY --from=0 /target/app.jar app.jar

RUN addgroup -S spring && adduser -S spring -G spring
RUN chown -R spring:spring /app
RUN chmod 755 /app

EXPOSE 8080


USER spring:spring



#ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.awt.headless=true", "-jar", "/app/app.jar"]