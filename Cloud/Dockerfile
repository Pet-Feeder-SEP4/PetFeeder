FROM openjdk:17-jdk-slim
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ./target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
