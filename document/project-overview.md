# Holashop Backend - Project Overview

**Shop App Backend** provides a robust RESTful API for e-commerce web and mobile applications. It leverages the Spring Cloud ecosystem to demonstrate microproservice-based architecture patterns (though currently implemented as a modular monolith).

## Introduction

Welcome to the backend component of `holashop-backend`. This Java Spring Boot application handles server-side logic, data processing, and provides essential APIs for the e-commerce platform.

## Key Features

- **Comprehensive API**: Covers User, Product, Order, Category, and Comment management.
- **Security**: JWT-based stateless authentication and Role-Based Access Control (RBAC).
- **Performance**: Redis caching for high-speed data retrieval.
- **Documentation**: Integrated Swagger UI for API exploration.
- **Database Management**: Flyway for versioned database migrations.
- **Monitoring**: Spring Boot Actuator for health and metric monitoring.

## Technology Stack

- **Core**: Java 17, Spring Boot 3.x
- **Database**: MySQL 8.x
- **ORM**: Hibernate / Spring Data JPA
- **Caching**: Redis (Lettuce client)
- **Migration**: Flyway
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 17+
- Maven
- MySQL 8.x
- Docker (optional, for containerized run)

### Installation & Run

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/chungdt03/holashop-backend.git
    cd holashop-backend
    ```

2.  **Configure Database:**
    Update `src/main/resources/application.yml` with your MySQL credentials.

3.  **Build the project:**
    ```bash
    mvn clean install
    ```

4.  **Run with Maven:**
    ```bash
    mvn spring-boot:run
    ```

    *Alternatively, use Docker Compose:*
    ```bash
    docker-compose up -d
    ```

5.  **Access Swagger UI:**
    Open [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) to explore the APIs.
