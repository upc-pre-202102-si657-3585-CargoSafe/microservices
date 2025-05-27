FROM eclipse-temurin:22-jdk

WORKDIR /app

# Usa el nombre correcto del JAR
COPY target/iam-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
