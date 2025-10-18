FROM eclipse-temurin:24-jdk-alpine as builder

COPY .. .

RUN chmod +x mvnw
RUN ./mvnw clean package -pl file-service -am -DskipTests

FROM eclipse-temurin:24-jre-alpine

COPY --from=builder /file-service/target/file-service-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar", "-Dspring.profiles.active=prod"]