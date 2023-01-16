FROM openjdk:11-jdk
ARG JAR_FILE=./build/libs/restful-server-1.2.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080:8080
