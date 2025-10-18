FROM eclipse-temurin:24-jdk-alpine AS builder

WORKDIR /app
COPY .. .

RUN chmod +x mvnw
RUN ./mvnw clean package -pl task-service -am -DskipTests

#__________________________________________________________________________
FROM eclipse-temurin:24-jre-alpine

WORKDIR /app
COPY --from=builder /app/task-service/target/task-service-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]