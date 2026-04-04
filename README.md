# Finance-dashboard-backend

# Overview

The Finance Dashboard Backend is a production-ready RESTful API built with Spring Boot 4.x and Java 25. It provides secure, role-based access to personal finance management features, including financial record tracking, real-time analytics, and a dynamic dashboard. Designed for scalability and maintainability, this backend supports token-based authentication using JWT and provides a robust foundation for any personal finance application.

# Technology Stack
| Layer      | Technology                        |
| ---------- | --------------------------------- |
| Language   | Java 25                           |
| Framework  | Spring Boot 4.0.5                 |
| Security   | Spring Security + JWT (JJWT 0.13) |
| Database   | PostgreSQL 18                     |
| ORM        | Hibernate 7 / Spring Data JPA     |
| API Docs   | SpringDoc OpenAPI 2.8             |
| Mapping    | ModelMapper 3.0                   |
| Build Tool | Maven                             |
| Utilities  | Lombok                            |

# Key Features
**JWT Authentication**: Supports stateless, token-based authentication via HTTP headers and cookies.

**Role-Based Access Control**: Three predefined roles: ROLE_ADMIN, ROLE_ANALYST, ROLE_VIEWER.

**Financial Records Management**: Full CRUD operations with filtering by category, date range, transaction type, and user.

**Dashboard Analytics**: Displays total income and expenses, net balance, category breakdowns, monthly trends, and recent transactions.

**Pagination & Sorting**: List endpoints support configurable sorting and pagination.

**Global Exception Handling**: Centralized and structured error responses across all endpoints.

**Swagger UI**: Interactive API documentation accessible at /swagger-ui/index.html.

# Project Structure
src/main/java/com/finance/dashboard/

├── config/                   # Application configuration, Swagger setup, constants

├── controller/               # REST controllers

│   ├── auth/                 # Authentication endpoints

│   ├── records/              # Financial records endpoints

│   └── dashboard/            # Dashboard endpoints

├── dto/                      # Data Transfer Objects (DTOs)

│   ├── auth/

│   ├── records/

│   └── dashboard/
├── exceptions/               # Custom exceptions and global exception handler

├── model/                    # JPA entities

├── repository/               # Spring Data repositories

├── security/                 # Security layer

│   ├── jwt/                  # JWT filters, utils, and entry points

│   ├── service/              # UserDetailsService implementation

│   └── dto/                  # Auth request and response DTOs

├── service/                  # Business logic (interfaces + implementations)

└── utils/                     # Generic utility classes

# Getting Started

**Prerequisites:**

**Java 25**

**Maven 3.9+**

**PostgreSQL 18**

# Database Setup

**Create a PostgreSQL database**:

CREATE DATABASE Finance_Dashboard;

# Environment Variables

**Windows (PowerShell):**

$env:DB_URL="jdbc:postgresql://localhost:5432/Finance_Dashboard"

$env:DB_USER="postgres"

$env:DB_PASS="yourpassword"

$env:JWT_SECRET="yourBase64EncodedSecretKeyHere"

$env:JWT_EXPIRY="86400000"

$env:JWT_COOKIE="financeDashboard"


# Running the Application
mvn clean install
mvn spring-boot:run

The backend server will start at: http://localhost:8080

# API Endpoints
**Authentication** — /api/auth

| Method | Endpoint    | Access        | Description              |
| ------ | ----------- | ------------- | ------------------------ |
| POST   | `/signup`   | Public        | Register a new user      |
| POST   | `/signin`   | Public        | Login and receive JWT    |
| POST   | `/signout`  | Public        | Clear JWT cookie         |
| GET    | `/user`     | Authenticated | Get current user details |
| GET    | `/username` | Authenticated | Get current username     |

**Example Signin Request:**

{

  "username": "admin",
  
  "password": "adminPass"
  
}

**Example Signin Response:**

{

  "id": 1,
  
  "username": "admin",

  "roles": ["ROLE_ADMIN"],
  
  "jwtToken": "eyJhbGciOiJIUzI1NiJ9..."
  
}

**Financial Records** — /api

| Method | Endpoint                                 | Role           | Description                          |
| ------ | ---------------------------------------- | -------------- | ------------------------------------ |
| POST   | `/admin/categories/{categoryId}/records` | ADMIN          | Create a financial record            |
| GET    | `/analyst/records`                       | ANALYST, ADMIN | Get all records (paginated)          |
| GET    | `/analyst/records/{financialId}`         | ANALYST, ADMIN | Get record by ID                     |
| GET    | `/analyst/records/category/{categoryId}` | ANALYST, ADMIN | Get records by category              |
| GET    | `/analyst/records/date-range`            | ANALYST, ADMIN | Get records by date range            |
| GET    | `/analyst/records/type/{type}`           | ANALYST, ADMIN | Get records by type (INCOME/EXPENSE) |
| GET    | `/analyst/records/user/{userId}`         | ANALYST, ADMIN | Get records by user                  |
| PUT    | `/admin/records/{financialId}`           | ADMIN          | Update a record                      |
| DELETE | `/admin/records/{financialId}`           | ADMIN          | Delete a record                      |

**Categories** — /api

| Method | Endpoint                         | Role           | Description                    |
| ------ | -------------------------------- | -------------- | ------------------------------ |
| POST   | `/admin/categories`              | ADMIN          | Create a category              |
| GET    | `/analyst/categories`            | ANALYST, ADMIN | Get all categories (paginated) |
| PUT    | `/admin/categories/{categoryId}` | ADMIN          | Update a category              |
| DELETE | `/admin/categories/{categoryId}` | ADMIN          | Delete a category              |

**Dashboard** — /api/public

| Method | Endpoint     | Access | Description                |
| ------ | ------------ | ------ | -------------------------- |
| GET    | `/dashboard` | Public | Get full dashboard summary |



**Example Response:**

{

  "totalIncome": 50000.0,
  
  "totalExpense": 20000.0,
  
  "netBalance": 30000.0,
  
  "categoryTotals": [...],
  
  "recentTransactions": [...],
  
  "monthlyTrend": [...]
  
}
# Security
Protected endpoints require a **Bearer** token in the Authorization header:

Authorization: Bearer <your_jwt_token>

Tokens are also supported via HTTP cookies (financeDashboard) for browser clients.

**Default Development Users:**

| Username | Password  | Role                                  |
| -------- | --------- | ------------------------------------- |
| viewer1  | password1 | ROLE_VIEWER                           |
| analyst1 | password2 | ROLE_ANALYST                          |
| admin    | adminPass | ROLE_ADMIN, ROLE_ANALYST, ROLE_VIEWER |


**Important:** Always change default credentials before deploying to production.

# API Documentation

**Swagger UI is available at:**

http://localhost:8080/swagger-ui/index.html

Use the Authorize button in Swagger to test protected endpoints with a JWT token.

# Developer

**Siddharth**

**GitHub**: github.com/Siddharth-del

**LinkedIn:** linkedin.com/in/siddharth-java-dev

**Email:** GautamSiddharth131004@gmail.com