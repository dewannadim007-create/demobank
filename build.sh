#!/usr/bin/env bash
# Build script for Render deployment

set -e  # Exit on error

echo "===================="
echo "Starting build process"
echo "===================="

# Print Java version
echo "Java version:"
java -version

# Print Maven version
echo "Maven version:"
mvn -version

# Clean and build the application
echo "Building application..."
mvn clean package -DskipTests

echo "===================="
echo "Build completed successfully!"
echo "===================="
