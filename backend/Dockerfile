# Use a slim JRE image for the runtime stage
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy the pre-built JAR file from the target directory
COPY target/ecommerce-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","/app/app.jar"]
