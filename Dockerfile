FROM openjdk:11-jdk AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src
COPY conf conf
RUN chmod +x ./gradlew
RUN ./gradlew :bootJar --debug

FROM openjdk:11-jdk
ARG JAR_FILE=frolic-sns-1.2.0-SNAPSHOT.jar
COPY --from=builder build/libs/${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
