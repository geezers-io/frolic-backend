FROM openjdk:15-jdk
ARG JAR_FILE=./build/libs/restful-server-0.0.1-SNAPSHOT-aot.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
