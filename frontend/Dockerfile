# Stage 1: Build the Angular application
FROM node:20-alpine AS build

WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application source code
COPY . .

# Build the application for production
RUN npm run build

# Stage 2: Serve the application from a lightweight web server
FROM nginx:1.25-alpine

# Copy the build output from the build stage
COPY --from=build /app/dist/ecommerce-frontend /usr/share/nginx/html

# Expose port 80
EXPOSE 80
