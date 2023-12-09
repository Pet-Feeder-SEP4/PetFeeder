FROM openjdk:17-jdk-slim
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ./target/PetFeederCloud-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
