# 📝 Task Tracker API

A simple yet well-structured **RESTful API for task management**, built using **Spring Boot** and **PostgreSQL**. This project demonstrates clean architecture, request validation, custom error handling, Swagger documentation, and unit testing with JUnit 5 + Mockito.

---

## 🚀 Features

- ✅ CRUD operations for tasks
- ✅ Request validation with `@Valid`
- ✅ Custom error responses using `@ControllerAdvice`
- ✅ OpenAPI (Swagger UI) documentation
- ✅ Persistence with PostgreSQL
- ✅ Unit tests for service and controller layers

---

## 🛠 Tech Stack

- Java 21
- Spring Boot 3.2.5
- Spring Data JPA
- Spring Validation
- Springdoc OpenAPI
- PostgreSQL
- JUnit 5 & Mockito

---

## 📦 Getting Started

### 1. Create the database

```sql
CREATE DATABASE tasktracker;
CREATE USER task_user WITH PASSWORD 'secret123';
GRANT ALL PRIVILEGES ON DATABASE tasktracker TO task_user;
```
### 2. Configure application.yml

```yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/tasktracker
    username: task_user
    password: secret123
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
```

### 3. Run the project
```bash
   ./mvnw spring-boot:run
   ```
### 4. Open Swagger UI
   Visit: http://localhost:8080/swagger-ui.html

### ✅ API Endpoints
```Method	Endpoint	Description
POST	/tasks	Create a new task
GET	/tasks	List all tasks
PUT	/tasks/{id}	Update a task
PATCH	/tasks/{id}/done	Mark task as done
PATCH	/tasks/{id}/in-progress	Mark task in progress
DELETE	/tasks/{id}	Delete a task
```
### 🧪 Running Tests
```bash
./mvnw test
```
All core logic is covered by unit tests for both service and controller layers.

### 📁 Project Structure
```csharp
com.saparbek.Task_Tracker
├── controller        # REST endpoints
├── dto               # Request/response models
├──  exception         # Global error handling
├── model             # JPA entities
├── repository        # Spring Data repositories
├── service           # Business logic
```
### 📌 Author
### Saparbek Kozhanazar
Feel free to contribute, fork, or raise issues!