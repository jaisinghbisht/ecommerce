# Ecommerce Application

This is a full-stack e-commerce application built with Spring Boot and Angular. It provides a simple and easy-to-use platform for managing and selling products online.

## Features

*   **User Authentication:** Secure user authentication using JWT (JSON Web Tokens).
*   **Product Management:** CRUD (Create, Read, Update, Delete) operations for products.
*   **RESTful API:** A well-defined RESTful API for seamless communication between the frontend and backend.
*   **Database Migrations:** Database schema management using Flyway.
*   **Containerization:** Docker and Docker Compose support for easy setup and deployment.

## Architecture

The application is divided into two main parts:

*   **Backend:** A Spring Boot application that provides the RESTful API. It's responsible for handling business logic, data persistence, and security.
*   **Frontend:** An Angular application that provides the user interface. It communicates with the backend using the RESTful API.

## Technologies Used

*   **Backend:**
    *   Java 17
    *   Spring Boot 3
    *   Spring Web
    *   Spring Data JPA
    *   Spring Security
    *   PostgreSQL
    *   Flyway
    *   Maven
*   **Frontend:**
    *   Angular 13
    *   TypeScript
    *   HTML/CSS

## Prerequisites

*   Java 17
*   Node.js and npm
*   Angular CLI
*   PostgreSQL
*   Docker
*   Docker Compose

## Getting Started

### Without Docker

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/ecommerce.git
    cd ecommerce
    ```
2.  **Configure the database:**
    *   Make sure you have a PostgreSQL database running.
    *   Open `backend/src/main/resources/application.properties` and update the following properties:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/your-database
        spring.datasource.username=your-username
        spring.datasource.password=your-password
        ```
3.  **Build and run the backend:**
    ```bash
    cd backend
    ./mvnw clean install
    ./mvnw spring-boot:run
    ```
    The backend will be running on `http://localhost:8080`.
4.  **Build and run the frontend:**
    *   Open a new terminal and navigate to the `frontend` directory:
        ```bash
        cd frontend
        ```
    *   Install the dependencies:
        ```bash
        npm install
        ```
    *   Start the Angular development server:
        ```bash
        ng serve
        ```
    The frontend will be running on `http://localhost:4200`.

### With Docker

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/ecommerce.git
    cd ecommerce
    ```
2.  **Start the application:**
    ```bash
    docker compose up -d
    ```
    This will build the Docker images and start the application. The application will be available at `http://localhost:8080`.

## How to check app health & logs

*   **Health endpoint (local / container):**
    *   Ensure the app is running (e.g., `docker compose up -d`).
    *   Query the actuator health endpoint:
        ```bash
        curl -sS http://localhost:8080/actuator/health
        ```
    *   Expect a JSON response containing `"status":"UP"`.
*   **Container logs:**
    *   Follow backend logs with compose: `docker compose logs -f backend`
    *   Or for a single container: `docker logs -f <container_name>`

## CI / Actions - interpreting failures

If the CI run fails:

*   Open the Actions tab for the failing workflow run and expand the failed job.
*   Inspect the logs of the failing step (Maven build, test reporting, or Docker build). Maven test failures appear in the "Build and run tests" step.
*   Test results are uploaded as an artifact named `surefire-reports` and published to the Checks via the JUnit reporter. Download the artifact to inspect detailed surefire XML and test output.
*   Common fixes: fix failing unit tests, adjust memory/MAVEN_OPTS, or ensure that required services (e.g., DB) are mocked or available for CI tests.
*   Persisting issues? Copy the failing step's logs and the surefire-reports artifact when opening an issue.
