FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/*.jar transaction-service.jar

ENTRYPOINT ["java", "-jar", "transaction-service.jar"]