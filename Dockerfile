FROM openjdk:17-jdk-slim

WORKDIR /app
COPY target/CrossTrafficSimulator-0.0.1-SNAPSHOT.jar simulator.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "simulator.jar"]