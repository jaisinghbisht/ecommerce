# Ecommerce Application

This is a Spring Boot application for an e-commerce website. It uses PostgreSQL as the database.

## Prerequisites
- Docker
- Docker Compose

## Getting Started
1. Clone the repository.
2. Build the Docker image using the Dockerfile.
3. Start the application using `docker compose up -d`.

## Configuration
The application uses environment variables for configuration. The following environment variables are used:
- `SPRING_DATASOURCE_URL`: The URL of the PostgreSQL database.
- `SPRING_DATASOURCE_USERNAME`: The username for the PostgreSQL database.
- `SPRING_DATASOURCE_PASSWORD`: The password for the PostgreSQL database.

## How to check app health & logs
- Health endpoint (local / container)
  - Ensure the app is running (e.g. `docker compose up -d`).
  - Query the actuator health endpoint:
    - curl: `curl -sS http://localhost:8080/actuator/health`
    - Expect a JSON response containing `"status":"UP"`. If DB checks are disabled in CI or local env, set `HEALTH_DB_ENABLED=false` to skip DB health.
- Container logs
  - Follow backend logs with compose: `docker compose logs -f backend`
  - Or for a single container: `docker logs -f <container_name>`
- In CI (GitHub Actions)
  - Check the Actions tab -> failing workflow -> expand the job -> inspect step logs (Maven build, tests, Docker build).
  - Test results are uploaded as an artifact named `surefire-reports`. Download it to inspect surefire XML and test output.
  - The workflow also creates a test reporter check run (Unit tests). Ensure workflow permissions allow `checks: write` so the reporter can publish results.
  - If you run the built image inside CI for health checks, pass `-e HEALTH_DB_ENABLED=false` to the container if no DB service is available.
- Troubleshooting
  - If `/actuator/health` is not UP, inspect application logs for exceptions (startup DB configuration errors are common).
  - For DB issues, verify the datasource env vars and that the DB container is healthy (`docker compose ps` / `docker compose logs db`).

## CI / Actions - interpreting failures
If the CI run fails:
- Open the Actions tab for the failing workflow run and expand the failed job.
- Inspect the logs of the failing step (Maven build, test reporting, or Docker build). Maven test failures appear in the "Build and run tests" step.
- Test results are uploaded as an artifact named `surefire-reports` and published to the Checks via the JUnit reporter. Download the artifact to inspect detailed surefire XML and test output.
- Common fixes: fix failing unit tests, adjust memory/MAVEN_OPTS, or ensure that required services (e.g., DB) are mocked or available for CI tests.
- Persisting issues? Copy the failing step's logs and the surefire-reports artifact when opening an issue.