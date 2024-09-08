FROM openjdk:21-jdk-slim

COPY build/libs/producer-0.0.1-SNAPSHOT.jar /app/producer.jar

ENTRYPOINT ["java", "-jar", "/app/producer.jar"]