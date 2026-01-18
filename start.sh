#!/usr/bin/env bash
# Start script for Render deployment

set -e  # Exit on error

echo "===================="
echo "Starting application"
echo "===================="

# Set the PORT environment variable (Render provides this)
export PORT=${PORT:-8080}

# Run the Spring Boot application
java -Dserver.port=$PORT \
     -Dspring.data.mongodb.uri=${MONGODB_URI} \
     -jar target/springboot-app-0.0.1-SNAPSHOT.jar
