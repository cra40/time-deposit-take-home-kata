# Time Deposit Refactoring Kata - Take-Home Assignment

Application is developed using the preferred tech stack given in the requirements and embraces the hexagonal architectural pattern.
The domain/business logic hexagon is a java library which is wired to a spring boot application in the infratructure/framework hexagon. Adapters are plugged in/out of the infrastructure layer.

### Prerequisites
- Java 17 or higher
- Maven 3.9 or higher
- Docker 25 or higher

### Running
- Start postgres `docker-compose up -d`
- Start application `mvn spring-boot:run`
- Try out the apis from swagger ui, http://localhost:8080/swagger-ui/index.html

### Swagger
- OpenAPI docs are available at http://localhost:8080/api-docs
- Swagger UI available at http://localhost:8080/swagger-ui/index.html

### APIs available
- GET `/time-deposits`: Retrieve all time deposits
- POST `/time-deposits/interests`: No request body is needed. It updates monthly interests for all time deposits and returns the updated time deposits
- The requests can be tried out in Swagger UI or using any tools like curl or postman

### Assumptions made
- Deprecations are allowed
- Changes made to the existing codebase should be non breaking but there is no requirement to use the existing classes in new implementations