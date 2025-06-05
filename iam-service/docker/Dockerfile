# Etapa 1: build
FROM maven:3.9.6-eclipse-temurin-22 as builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:22-jdk

WORKDIR /app

# Usa el nombre correcto del JAR
COPY --from=builder /app/target/iam-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
