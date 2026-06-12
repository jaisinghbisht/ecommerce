@echo off
setlocal

REM --- Set Working Directory ---
REM Change directory to the project root (the parent directory of this script)
cd /d "%~dp0.."
IF %ERRORLEVEL% NEQ 0 (
    echo "ERROR: Failed to change directory to project root."
    exit /b %ERRORLEVEL%
)

ECHO "Building backend JAR..."
REM Change directory to the backend folder to run the build
cd backend
IF %ERRORLEVEL% NEQ 0 (
    echo "ERROR: Failed to change directory to backend. Are you in the project root?"
    exit /b %ERRORLEVEL%
)

REM Build the project. The target directory will now be created inside ./backend/
CALL mvnw.cmd clean package -DskipTests
IF %ERRORLEVEL% NEQ 0 (
    echo "ERROR: Backend build failed."
    exit /b %ERRORLEVEL%
)

REM Return to the root directory to run docker-compose
cd ..

ECHO "Starting Docker containers..."
docker compose up --build -d
IF %ERRORLEVEL% NEQ 0 (
    echo "ERROR: Docker Compose failed to start."
    exit /b %ERRORLEVEL%
)

echo "Services started successfully."
endlocal
