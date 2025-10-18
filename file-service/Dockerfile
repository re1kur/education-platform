FROM eclipse-temurin:24-jdk-alpine AS builder

WORKDIR /app
COPY .. .

RUN chmod +x mvnw
RUN ./mvnw clean package -pl file-service -am -DskipTests

#_______________________________________________________________________________________________
FROM eclipse-temurin:24-jre-alpine

WORKDIR /app
COPY --from=builder /app/file-service/target/file-service-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar", "-Dspring.profiles.active=prod"]