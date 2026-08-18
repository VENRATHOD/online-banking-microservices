FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/*.jar api-gateway.jar

ENTRYPOINT ["java", "-jar", "api-gateway.jar"]