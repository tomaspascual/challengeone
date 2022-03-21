FROM openjdk:12-alpine

MAINTAINER Tomas Pascual

RUN apk add bash

WORKDIR /app

RUN pwd
RUN ls -l

# Install curl
RUN apk --no-cache add curl

# Install Maven
ENV M2_HOME /app/.mvn
RUN curl -s --retry 3 -L https://lang-jvm.s3.amazonaws.com/maven-3.3.3.tar.gz | tar xz -C /app
RUN chmod +x /app/.maven/bin/mvn
ENV M2_HOME /app/.maven
ENV PATH /app/.maven/bin:$PATH
ENV MAVEN_OPTS "-Xmx1024m -Duser.home=/app/usr -Dmaven.repo.local=/app/.m2/repository"

# Run Maven to cache dependencies
ONBUILD COPY ["pom.xml", "*.properties", "/app/user/"]
ONBUILD RUN ["mvn", "dependency:resolve"]
ONBUILD RUN ["mvn", "verify"]

ONBUILD COPY . /app/user/
ONBUILD RUN ["mvn", "-DskipTests=true", "clean", "package"]

COPY data/deniro.csv deniro.csv
COPY data/chinook.db chinook.db
COPY --from=0 /target/app.jar app.jar

RUN addgroup -S spring && adduser -S spring -G spring
RUN chown -R spring:spring /app
RUN chmod 755 /app

EXPOSE 8080


USER spring:spring



ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.awt.headless=true", "-jar", "/app/app.jar"]