FROM openjdk:12-alpine

RUN apk add bash

WORKDIR /app

COPY chinook.db chinook.db
COPY target/app.jar app.jar

EXPOSE 8080

#RUN addgroup -S spring && adduser -S spring -G spring
#RUN chown -R spring:spring /app
#RUN chmod 755 /app
#USER spring:spring



ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.awt.headless=true", "-jar", "/app/app.jar"]