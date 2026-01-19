Spring Boot Web Development ProjectThis project demonstrates web development concepts using Spring Boot and modern technologies. It serves as a practical implementation of a full-stack Java application.System ArchitectureWeb & API Architecturespring-boot-starter-web: Uses the standard MVC web framework with an embedded Tomcat server for handling requests.spring-boot-starter-webflux: Implements the reactive web stack to utilize WebClient for non-blocking HTTP calls.Databasespring-boot-starter-data-mongodb: Handles data persistence through MongoDB integration and Spring Data repositories.Frontend / UIspring-boot-starter-thymeleaf: Acts as the server-side template engine to render dynamic HTML pages.Security & Validationspring-boot-starter-security: Manages authentication and authorization protocols.spring-boot-starter-validation: Enforces bean validation rules using Hibernate Validator.Developer Toolsspring-boot-devtools: Enabled for a better coding experience, offering auto-restart and LiveReload features.Testingspring-boot-starter-test: The main testing suite using JUnit 5, Mockito, and AssertJ.spring-security-test: Utilities specifically for testing security configurations.reactor-test: Support for testing reactive components.System RequirementsJava 17 or higherMaven 3.6+MongoDB (Must be running locally on port 27017 or configured in application.properties)Setup and Installation1. Navigate to the project directoryBashcd d:\SPRINGBOOT_TRY
2. Set up the DatabaseUpdate the configuration file located at src/main/resources/application.properties with your MongoDB connection string:Propertiesspring.data.mongodb.uri=mongodb://localhost:27017/springboot_db
3. Install dependenciesRun the following command to download dependencies and build the project:Bashmvn clean install
4. Run the applicationStart the application with:Bashmvn spring-boot:run
Once started, the application will be accessible at http://localhost:8080.Folder Structurespringboot-app/
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
API and Page EndpointsWeb Pages (Thymeleaf)GET / - Home pageGET /about - About pageREST API (JSON)GET /api/hello - Simple greeting endpointGET /api/health - Health check endpointSecurity ConfigurationDefault credentials for local testing and evaluation:Username: adminPassword: admin123Important: These credentials are for development use only and should be changed before any real-world deployment.Development ToolsThe project includes spring-boot-devtools to assist with development:Automatic restart triggers when code changes are saved.LiveReload triggers a browser refresh when resources change.Ensure DevTools is enabled in your IDE to use these features.Running TestsTo execute the test suite, run:Bashmvn test
Build ProcessTo compile the project into a production-ready JAR file:Bashmvn clean package
To run the compiled JAR file:Bashjava -jar target/springboot-app-0.0.1-SNAPSHOT.jar
Deployment GuideThe application is configured for deployment on platforms like Render using Docker. Please refer to DEPLOYMENT.md for specific instructions on:Setting up MongoDB AtlasDeploying via DockerEnvironment variable configurationTroubleshootingTechnology StackCategoryTechnologyPurposeWeb FrameworkSpring MVCServlet-based web applicationsReactiveSpring WebFluxNon-blocking HTTP client (WebClient)DatabaseMongoDBNoSQL document databaseTemplate EngineThymeleafServer-side HTML renderingSecuritySpring SecurityAuthentication & authorizationValidationHibernate ValidatorInput validationJSON ProcessingJacksonObject-JSON serializationTestingJUnit 5, MockitoUnit and integration testingImplementation NotesThe project intentionally includes both Web and WebFlux dependencies.The standard Web module handles the primary blocking request/response cycle.WebFlux is used specifically to access WebClient for making reactive HTTP calls to external APIs.Jackson is listed for clarity and version control, though it is implicitly included in spring-boot-starter-web.Future ImprovementsFeel free to suggest new features or improvements to extend the functionality of this application.LicenseThis project is created for educational purposes.
