![](images/logo_images.png)

# Holashop Backend

A RESTful API backend service for e-commerce platform built with Spring Boot.

## Introduction

Holashop Backend is a complete e-commerce backend system that provides RESTful APIs for web and mobile applications. The system handles user authentication, product management, order processing, and more, following modern Java enterprise architecture patterns.

## Technology Stack

**Backend Framework:**
- Java 17
- Spring Boot 3.x
- Spring Security (JWT Authentication)
- Spring Data JPA
- Hibernate ORM

**Database:**
- MySQL 8.x
- Flyway (Database Migration)
- Redis (Caching)

**Build & DevOps:**
- Maven
- Docker & Docker Compose

**API Documentation:**
- SpringDoc OpenAPI 3 (Swagger)
- Postman Collection

**Messaging:**
- Apache Kafka
- Zookeeper

## Features

- User authentication and authorization (JWT-based)
- Role-based access control (USER, ADMIN)
- Product catalog management
- Category management
- Order processing and tracking
- Shopping cart functionality
- Comment and review system
- Coupon and discount management
- Image upload and management
- RESTful API endpoints
- Comprehensive exception handling
- Request/Response validation
- Database migration with Flyway

## Prerequisites

Before you begin, ensure you have the following installed:

- JDK 17 or higher
- Maven 3.6+
- Docker and Docker Compose
- MySQL 8.x (or use Docker)

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/chungdt03/holashop-backend.git
cd holashop-backend
```

### 2. Configure environment

Update `application.yml` or create `.env` file with your database configuration:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3307/holashop
    username: your_username
    password: your_password
```

### 3. Start Docker services

```bash
docker-compose up -d
```

This will start MySQL, Redis, Kafka, and Zookeeper.

### 4. Build the project

```bash
mvn clean install
```

### 5. Run the application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8088`

### 6. Database Migration

Flyway will automatically run migrations on startup. Initial roles will be created:
- USER (id: 1)
- ADMIN (id: 2)

## Database Schema

![Database Diagram](images/database_diagram.jpg)

The database includes the following main entities:
- Users and Roles
- Products and Categories
- Orders and Order Details
- Comments
- Coupons and Coupon Conditions
- Product Images
- Social Accounts
- Tokens (JWT)

## API Documentation

### Swagger UI

Interactive API documentation is available at:

**URL:** http://localhost:8088/swagger-ui.html

**Features:**
- Interactive API testing
- Request/Response schemas
- Authentication support
- Auto-generated from code annotations

**Using Authentication:**
1. Get JWT token from Login endpoint
2. Click "Authorize" button
3. Enter: `Bearer <your-token>`
4. Test authenticated endpoints

### Postman Collection

Complete Postman collection with 35+ endpoints is available in `document/api-test/`

**Files:**
- `Holashop.postman_collection.json` - Main collection
- `Holashop.postman_environment.json` - Environment variables
- `POSTMAN_GUIDE.md` - Detailed guide
- `README.md` - Quick start guide

**Quick Start:**
1. Import both JSON files into Postman
2. Select "Holashop Environment"
3. Run Login request to get JWT token
4. Token is automatically saved for subsequent requests

**Documentation:** [document/api-test/README.md](document/api-test/README.md)

## Project Structure

```
holashop-be/
├── src/main/java/com/chungdt03/holashopbe/
│   ├── controllers/       # REST Controllers
│   ├── services/          # Business Logic
│   ├── repositories/      # Data Access Layer
│   ├── models/            # Entity Models
│   ├── dtos/              # Data Transfer Objects
│   ├── configurations/    # Spring Configurations
│   ├── exceptions/        # Exception Handlers
│   ├── filters/           # JWT Filter
│   └── utils/             # Utility Classes
├── src/main/resources/
│   ├── application.yml    # Application Configuration
│   ├── i18n/              # Internationalization
│   └── dev/db/migration/  # Flyway Migrations
├── document/              # Documentation
│   ├── api-test/          # Postman Collection
│   └── api-list.md        # API Reference
└── docker-compose.yml     # Docker Services
```

## API Endpoints

The API provides endpoints for:

**Authentication & Users**
- POST `/api/v1/users/register` - User registration
- POST `/api/v1/users/login` - User login
- POST `/api/v1/users/refresh-token` - Refresh JWT token
- GET `/api/v1/users/details` - Get user details
- PUT `/api/v1/users/details/{userId}` - Update user details

**Products**
- GET `/api/v1/products` - List products (with pagination)
- GET `/api/v1/products/{id}` - Get product details
- POST `/api/v1/products` - Create product (ADMIN)
- PUT `/api/v1/products/{id}` - Update product (ADMIN)
- DELETE `/api/v1/products/{id}` - Delete product (ADMIN)

**Categories**
- GET `/api/v1/categories` - List categories
- POST `/api/v1/categories` - Create category (ADMIN)
- PUT `/api/v1/categories/{id}` - Update category (ADMIN)
- DELETE `/api/v1/categories/{id}` - Delete category (ADMIN)

**Orders**
- POST `/api/v1/orders` - Create order
- GET `/api/v1/orders/{id}` - Get order details
- GET `/api/v1/orders/user/{user_id}` - Get user orders
- PUT `/api/v1/orders/{id}` - Update order (ADMIN)
- DELETE `/api/v1/orders/{id}` - Delete order (ADMIN)

**Comments**
- GET `/api/v1/comments` - Get comments by product
- POST `/api/v1/comments` - Create comment
- PUT `/api/v1/comments/{id}` - Update comment
- DELETE `/api/v1/comments/{id}` - Delete comment

For complete API documentation, see [document/api-list.md](document/api-list.md)

## Configuration

Key configuration files:

**application.yml**
- Server port: 8088
- Database connection
- JWT settings
- File upload settings
- Kafka configuration

**docker-compose.yml**
- MySQL 8
- Redis
- Kafka & Zookeeper

**.env files**
- `.env.dev` - Development environment
- `.env.prod` - Production environment

## Security

The application implements:
- JWT-based authentication
- Role-based authorization (USER, ADMIN)
- Password encryption with BCrypt
- Request validation
- CORS configuration
- SQL injection prevention

## License

This project is licensed under the MIT License.

```
MIT License
Copyright (c) 2026 chungdt
```

## Author

**chungdt**

GitHub: [@chungdt03](https://github.com/chungdt03)

## Contact

For questions or support, please open an issue on GitHub.
