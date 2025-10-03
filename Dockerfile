FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy Maven wrapper and project files if building inside Docker
COPY . .

# Build the project
RUN ./mvnw clean package -DskipTests

# Copy the JAR to run
RUN cp target/*.jar app.jar

# Expose port and run
EXPOSE 8080
CMD ["java", "-jar", "app.jar", "--server.port=${PORT}"]
