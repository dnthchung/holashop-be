# Backend Dependencies & Technologies

Based on the `pom.xml` file, the `shopapp-backend` project utilizes the following technologies and dependencies:

## 1. Core Environment
*   **Java:** JDK 17
*   **Framework:** Spring Boot 3.2.5 (Starter Parent)

## 2. Web & API Development
*   **Spring Boot Starter Web:** Core library for building RESTful APIs using Spring MVC and Tomcat.
*   **Spring Boot Starter Validation:** Implementation of the Bean Validation API (Hibernate Validator).
*   **SpringDoc OpenAPI (Swagger UI):** (`org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0`) - Automated API documentation and testing interface.

## 3. Database & Persistence
*   **Spring Boot Starter Data JPA:** Persists data in SQL stores with Java Persistence API using Hibernate.
*   **MySQL Connector/J:** (`com.mysql:mysql-connector-j`) - JDBC driver for MySQL database.
*   **Flyway:** (`org.flywaydb:flyway-core`, `org.flywaydb:flyway-mysql`) - Version 10.13.0. Database migration tool for managing schema changes.

## 4. Caching & NoSQL
*   **Spring Boot Starter Data Redis:** Integration with Redis.
*   **Lettuce Core:** (`io.lettuce:lettuce-core`) - Scalable thread-safe Redis client.

## 5. Security & Authentication
*   **Spring Boot Starter Security:** Security framework for authentication and authorization.
*   **JJWT (Java JWT):** (`io.jsonwebtoken:jjwt-api`, `jjwt-impl`, `jjwt-jackson`) - Version 0.11.5. Used for generating, parsing, and validating JSON Web Tokens (JWT) for stateless authentication.

## 6. Utilities & Helper Libraries
*   **Lombok:** (`org.projectlombok:lombok`) - Reduces boilerplate code (getters, setters, constructors) via annotations.
*   **MapStruct:** (`org.mapstruct:mapstruct`) - Version 1.5.5.Final. Code generator for safe and fast bean mappings.
*   **ModelMapper:** (`org.modelmapper:modelmapper`) - Version 3.2.0. Intelligent object mapping library.
*   **Jackson Databind:** (`com.fasterxml.jackson.core:jackson-databind`) - JSON processor for Java.
*   **JavaFaker:** (`com.github.javafaker:javafaker`) - Version 1.0.2. Generates fake data for testing and populating the database.

## 7. Monitoring & Dev Tools
*   **Spring Boot Starter Actuator:** Provides production-ready features like health checks and metric monitoring.
*   **Spring Boot DevTools:** Provides fast application restarts and LiveReload for development.
*   **Spring Boot Starter Logging:** (with `ch.qos.logback:logback-classic`) - Default logging framework.

## 8. Testing
*   **Spring Boot Starter Test:** Core testing libraries including JUnit, Hamcrest, and Mockito.
