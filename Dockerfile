# Use Maven to build the app
# We use JDK 21 to match your pom.xml <java.version>
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the pom and source code to the container
COPY pom.xml .
COPY src ./src

# Build the jar file (skipping tests to save time during the build)
RUN mvn clean package -DskipTests

# Creates the actual running container
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built jar from the first step
COPY --from=build /app/target/project2backend-0.0.1-SNAPSHOT.jar app.jar

# Open port 8080 for the Spring Boot app
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]