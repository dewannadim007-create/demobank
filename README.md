---

# Spring Boot Web Development Project

This project demonstrates web development concepts using Spring Boot and modern technologies. It serves as a practical implementation of a full-stack Java application.

## System Architecture

### Web & API Architecture

* **spring-boot-starter-web**: Uses the standard MVC web framework with an embedded Tomcat server for handling requests.
* **spring-boot-starter-webflux**: Implements the reactive web stack to utilize WebClient for non-blocking HTTP calls.

### Database

* **spring-boot-starter-data-mongodb**: Handles data persistence through MongoDB integration and Spring Data repositories.

### Frontend / UI

* **spring-boot-starter-thymeleaf**: Acts as the server-side template engine to render dynamic HTML pages.

### Security & Validation

* **spring-boot-starter-security**: Manages authentication and authorization protocols.
* **spring-boot-starter-validation**: Enforces bean validation rules using Hibernate Validator.

### Developer Tools

* **spring-boot-devtools**: Enabled for a better coding experience, offering auto-restart and LiveReload features.

### Testing

* **spring-boot-starter-test**: The main testing suite using JUnit 5, Mockito, and AssertJ.
* **spring-security-test**: Utilities specifically for testing security configurations.
* **reactor-test**: Support for testing reactive components.

## System Requirements

* **Java 17** or higher
* **Maven 3.6+**
* **MongoDB** (Must be running locally on port 27017 or configured in `application.properties`)

## Setup and Installation

### 1. Navigate to the project directory

```bash
cd d:\SPRINGBOOT_TRY

```

### 2. Set up the Database

Update the configuration file located at `src/main/resources/application.properties` with your MongoDB connection string:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/springboot_db

```

### 3. Install dependencies

Run the following command to download dependencies and build the project:

```bash
mvn clean install

```

### 4. Run the application

Start the application with:

```bash
mvn spring-boot:run

```

Once started, the application will be accessible at `http://localhost:8080`.

## Folder Structure

```
springboot-app/
├── src/
│   ├── main/
│   │   ├── java/com/example/app/
│   │   │   ├── Application.java          # Main entry point
│   │   │   └── controller/
│   │   │       ├── HomeController.java   # Web page controller
│   │   │       └── ApiController.java    # REST API controller
│   │   └── resources/
│   │       ├── application.properties    # Configuration
│   │       ├── templates/                # Thymeleaf templates
│   │       │   └── index.html
│   │       └── static/                   # Static resources
│   │           ├── css/style.css
│   │           └── js/main.js
│   └── test/                             # Test files
└── pom.xml                               # Maven dependencies

```

## API and Page Endpoints

### Web Pages (Thymeleaf)

* `GET /` - Home page
* `GET /about` - About page

### REST API (JSON)

* `GET /api/hello` - Simple greeting endpoint
* `GET /api/health` - Health check endpoint

## Security Configuration

Default credentials for local testing and evaluation:

* **Username**: admin
* **Password**: admin123

**Important**: These credentials are for development use only and should be changed before any real-world deployment.

## Development Tools

The project includes `spring-boot-devtools` to assist with development:

* **Automatic restart** triggers when code changes are saved.
* **LiveReload** triggers a browser refresh when resources change.

Ensure DevTools is enabled in your IDE to use these features.

## Running Tests

To execute the test suite, run:

```bash
mvn test

```

## Build Process

To compile the project into a production-ready JAR file:

```bash
mvn clean package

```

To run the compiled JAR file:

```bash
java -jar target/springboot-app-0.0.1-SNAPSHOT.jar

```

## Deployment Guide

The application is configured for deployment on platforms like Render using Docker. Please refer to `DEPLOYMENT.md` for specific instructions on:

* Setting up MongoDB Atlas
* Deploying via Docker
* Environment variable configuration
* Troubleshooting

## Technology Stack

| Category | Technology | Purpose |
| --- | --- | --- |
| Web Framework | Spring MVC | Servlet-based web applications |
| Reactive | Spring WebFlux | Non-blocking HTTP client (WebClient) |
| Database | MongoDB | NoSQL document database |
| Template Engine | Thymeleaf | Server-side HTML rendering |
| Security | Spring Security | Authentication & authorization |
| Validation | Hibernate Validator | Input validation |
| JSON Processing | Jackson | Object-JSON serialization |
| Testing | JUnit 5, Mockito | Unit and integration testing |

## Implementation Notes

* The project intentionally includes both **Web** and **WebFlux** dependencies.
* The standard Web module handles the primary blocking request/response cycle.
* WebFlux is used specifically to access `WebClient` for making reactive HTTP calls to external APIs.


* **Jackson** is listed for clarity and version control, though it is implicitly included in `spring-boot-starter-web`.

## Future Improvements

Feel free to suggest new features or improvements to extend the functionality of this application.

## License

This project is created for educational purposes.
