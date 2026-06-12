#!/bin/bash
# Exit immediately if a command exits with a non-zero status.
set -e

# --- Build Backend ---
echo "Building backend JAR..."
(cd backend && ./mvnw clean package -DskipTests)

# --- Start Docker Containers ---
echo "Starting Docker containers..."
docker compose up --build -d
