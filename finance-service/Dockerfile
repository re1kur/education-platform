FROM eclipse-temurin:24-jdk-alpine AS builder

WORKDIR /app
COPY .. .

RUN chmod +x mvnw
RUN ./mvnw clean package -pl finance-service -am -DskipTests

#_______________________________________________________________________________________________
FROM eclipse-temurin:24-jre-alpine

WORKDIR /app
COPY --from=builder /app/finance-service/target/finance-service-0.0.1-SNAPSHOT.jar app.jar

RUN apk add --no-cache curl

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]