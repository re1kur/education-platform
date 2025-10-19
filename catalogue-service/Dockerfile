FROM eclipse-temurin:24-jdk-alpine AS builder

WORKDIR /app
COPY .. .

RUN chmod +x mvnw
RUN ./mvnw clean package -pl catalogue-service -am -DskipTests

#_______________________________________________________________________________________________________________________
FROM eclipse-temurin:24-jre-alpine

WORKDIR /app
COPY --from=builder /app/catalogue-service/target/catalogue-service-0.0.1-SNAPSHOT.jar app.jar

RUN apk add --no-cache curl

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]