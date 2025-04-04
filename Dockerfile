FROM maven:3.9.9-eclipse-temurin-21 AS build
COPY pom.xml /app/pom.xml
COPY src /app/src
WORKDIR /app
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
COPY --from=build /app/target/restApi-0.0.1-SNAPSHOT.jar /app/restApi.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/restApi.jar"]