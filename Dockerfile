FROM openjdk:8-jre-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
CMD exec java -jar app.jar
