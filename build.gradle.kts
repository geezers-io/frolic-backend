import com.ewerk.gradle.plugins.tasks.QuerydslCompile
import org.gradle.api.JavaVersion;
import java.io.BufferedReader
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Paths


val queryDslVersion = "5.0.0"

group = "com.frolic.sns"
version = "1.2.0-SNAPSHOT"
java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

plugins {
  val kotlinVersion = "1.8"

  java
  id("org.springframework.boot") version "2.7.5"
  id("io.spring.dependency-management") version "1.1.0"
  id("org.springframework.experimental.aot") version "0.12.1"
  id("org.hibernate.orm")
  id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
  idea
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
  querydsl.extendsFrom(configurations.compileClasspath.get())
}

repositories {
  mavenCentral()
  maven { url = uri("https://repo.spring.io/release") }
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-security")
  testImplementation("org.springframework.security:spring-security-test")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  implementation("org.springframework.boot:spring-boot-starter-aop")
  testImplementation("org.springframework.boot:spring-boot-starter-test")

  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")

  runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

  implementation("org.springframework.boot:spring-boot-starter-mail:2.7.8")

  implementation("org.springframework.boot:spring-boot-starter-data-redis")

  implementation("io.jsonwebtoken:jjwt-api:0.11.5")
  implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
  implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
  runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")

  implementation("com.querydsl:querydsl-core:${queryDslVersion}")
  implementation("com.querydsl:querydsl-jpa:${queryDslVersion}")
  implementation("com.querydsl:querydsl-apt:${queryDslVersion}")

  implementation("org.springdoc:springdoc-openapi-ui:1.6.14")
  implementation("org.springdoc:springdoc-openapi-security:1.6.14")

  implementation("com.twilio.sdk:twilio:8.8.0")

  implementation("org.mockito:mockito-core:4.11.0")

  implementation("com.google.code.findbugs:jsr305:3.0.2")

  implementation("com.amazonaws:aws-java-sdk:1.12.397")

}

tasks.withType<Test> {
  val file: File = File("$rootDir/conf/spring/.env").absoluteFile
  file.readLines(charset = Charsets.UTF_8).forEach {
    if (it.length > 2) {
      val (key, value) = it.split("=")
      environment(key, value)
    }
  }
  useJUnitPlatform()
}

tasks.withType<QuerydslCompile> {
  options.annotationProcessorPath = configurations.querydsl.get()
}

val querydslDir = "$buildDir/generated/querydsl"
querydsl {
  jpa = true
  querydslSourcesDir = querydslDir
}

sourceSets {
  main {
    java {
      srcDir(querydslDir)
    }
  }
}

//tasks.withType<QuerydslCompile> {
//  doFirst {
//    delete("$buildDir/generated/querydsl/com")
//  }
//
//  options.annotationProcessorPath {
//    configurations.querydsl
//  }
//}

//compileQuerydsl {
//  doFirst {
//    delete "$buildDir/generated/querydsl/com"
//  }
//  options.annotationProcessorPath = configurations.querydsl
//}
