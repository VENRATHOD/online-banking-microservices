FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/*.jar auth-service.jar

ENTRYPOINT ["java", "-jar", "auth-service.jar"]