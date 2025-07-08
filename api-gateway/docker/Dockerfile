# Etapa 1: build
FROM maven:3.9.6-eclipse-temurin-22 as builder
WORKDIR /app

# Configurar codificación UTF-8 para Maven
ENV MAVEN_OPTS="-Dfile.encoding=UTF-8"
ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:22-jdk

# Configurar codificación UTF-8 para Java
ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

WORKDIR /app

# Usa el nombre correcto del JAR
COPY --from=builder /app/target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
