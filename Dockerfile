# Stage 1: Build the application using a Maven image
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
# Download dependencies first to cache them
COPY pom.xml .
RUN mvn dependency:go-offline
# Copy the source code and build
COPY src ./src

# Build the application, skipping tests for faster build time
RUN mvn clean package -DskipTests

# Stage 2: Create the final lightweight runtime image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expose port (default spring boot port)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
