
FROM openjdk:17-jdk-slim


WORKDIR /demoJava17

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
