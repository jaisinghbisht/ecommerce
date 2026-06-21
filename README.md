# Full-Stack E-commerce Platform

This project is a complete full-stack e-commerce application featuring a Spring Boot 3 backend and an Angular frontend.

## Project Structure

This project is a monorepo containing two main sub-projects:

-   `/backend`: The Spring Boot REST API.
-   `/frontend`: The Angular single-page application (SPA).

---

## Backend (Spring Boot)

A robust, stateless REST API built with Spring Boot.

### Features

-   **Authentication:** Secure JWT-based authentication with refresh tokens.
-   **Authorization:** Enterprise-grade Role-Based Access Control (RBAC) using `@PreAuthorize`.
-   **Database:** PostgreSQL with Flyway for automated schema migrations.
-   **API Documentation:** Live API documentation available via Swagger UI.
-   **Testing:** Comprehensive unit and integration tests using JUnit 5 and Testcontainers.
-   **Containerization:** Docker-ready with a provided `Dockerfile`.

### Technology Stack

-   Java 17
-   Spring Boot 3
-   Spring Security
-   PostgreSQL
-   Maven
-   JWT
-   Testcontainers

### Prerequisites

-   JDK 17 or later
-   Maven 3.6 or later
-   Docker (for running a local PostgreSQL instance)

### How to Run the Backend

1.  **Start the Database:**
    The backend is configured to connect to a PostgreSQL database. The easiest way to run one is with Docker:
    ```sh
    docker run --name ecommerce-db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=password -e POSTGRES_DB=ecommerce -p 5432:5432 -d postgres
    ```

2.  **Run the Application:**
    Navigate to the `backend` directory and run the application using the Maven wrapper:
    ```sh
    cd backend
    ./mvnw spring-boot:run
    ```

3.  **Access the API:**
    -   The API will be available at `http://localhost:8080`.
    -   Swagger UI documentation can be accessed at `http://localhost:8080/swagger-ui.html`.

---

## Frontend (Angular)

A modern, scalable single-page application built with Angular.

### Features

-   **Architecture:** Scalable feature-based module structure with lazy loading.
-   **Core Services:** Centralized services for API communication, authentication, and storage.
-   **Authentication:** Full login/logout flow integrated with the backend's JWT authentication.
-   **Routing:** Protected routes using `AuthGuard` and `AdminGuard`.
-   **Reactive UI:** Built with RxJS for a responsive and dynamic user experience.
-   **Styling:** Styled with Bootstrap 5.

### Technology Stack

-   Angular
-   TypeScript
-   RxJS
-   Bootstrap 5

### Prerequisites

-   Node.js (LTS version recommended)
-   Angular CLI (`npm install -g @angular/cli`)

### How to Run the Frontend

1.  **Install Dependencies:**
    Navigate to the `frontend` directory and install the required npm packages:
    ```sh
    cd frontend
    npm install
    ```

2.  **Run the Development Server:**
    Start the Angular development server. The `--open` flag will automatically open it in your default browser.
    ```sh
    ng serve --open
    ```

3.  **Access the Application:**
    -   The frontend will be available at `http://localhost:4200`.
    -   The application will automatically proxy API requests to the backend running on `http://localhost:8080`.

---
