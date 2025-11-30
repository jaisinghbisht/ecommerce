# Ecommerce Application

This is a Spring Boot application for an e-commerce website. It uses PostgreSQL as the database.

## Prerequisites

- Docker
- Docker Compose

## Getting Started

1. Clone the repository.
2. Build the Docker image using the Dockerfile.
3. Start the application using docker-compose up.

## Configuration

The application uses environment variables for configuration. The following environment variables are used:

- SPRING_DATASOURCE_URL: The URL of the PostgreSQL database.
- SPRING_DATASOURCE_USERNAME: The username for the PostgreSQL database.
- SPRING_DATASOURCE_PASSWORD: The password for the PostgreSQL database.

# CI / Actions - interpreting failures

If the CI run fails:
- Open the Actions tab for the failing workflow run and expand the failed job.
- Inspect the logs of the failing step (Maven build, test reporting, or Docker build). Maven test failures appear in the "Build and run tests" step.
- Test results are uploaded as an artifact named `surefire-reports` and published to the Checks via the JUnit reporter. Download the artifact to inspect detailed surefire XML and test output.
- Common fixes: fix failing unit tests, adjust memory/MAVEN_OPTS, or ensure that required services (e.g., DB) are mocked or available for CI tests.

Persisting issues? Copy the failing step's logs and the surefire-reports artifact when opening an issue.
