# 📝 Task Tracker API

A clean, secure and modular **RESTful API for task management**, built with **Spring Boot**, **JWT authentication**, and **PostgreSQL**. This project demonstrates clean architecture, custom validation, role-based authorization, Swagger documentation, and unit testing with JUnit 5 + Mockito.

---

## 🚀 Features

- ✅ JWT-based authentication with login & registration
- ✅ Role-based access control (`USER` & `ADMIN`)
- ✅ Task CRUD operations with ownership validation
- ✅ Request validation with `@Valid`
- ✅ Global error handling using `@ControllerAdvice`
- ✅ Swagger UI with grouped endpoints and security
- ✅ PostgreSQL persistence
- ✅ Secure config via `.env` and dotenv loader
- ✅ Unit testing (service & controller layers)

---

## 🛠 Tech Stack

- Java 21
- Spring Boot 3.2.5
- Spring Security + JWT (JJWT)
- Spring Data JPA
- Spring Validation
- Springdoc OpenAPI
- PostgreSQL
- JUnit 5 + Mockito

---

## 📦 Getting Started

### 1. Create the database

```sql
CREATE DATABASE tasktracker;
CREATE USER task_user WITH PASSWORD 'secret123';
GRANT ALL PRIVILEGES ON DATABASE tasktracker TO task_user;
```
### 2. Configure .env file

```.env
JWT_SECRET=MySuperSecretKeyThatIsSecureEnough123!
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/tasktracker
SPRING_DATASOURCE_USERNAME=task_user
SPRING_DATASOURCE_PASSWORD=secret123
```
✅ The application loads these using DotenvInitializer.

### 3. Run the application
```bash
   ./mvnw spring-boot:run
   ```
### 4. Open Swagger UI
   Visit: http://localhost:8080/swagger-ui.html
   Use the "Authorize" button to test protected endpoints with JWT.

### 🔐 Authentication Endpoints
```Method	Endpoint	Description
POST	/auth/register	Register new user
POST	/auth/login	Login and receive JWT token
```
### ✅ Task Endpoints
``` Method	Endpoint	Description
POST	/tasks	Create task (for current user)
GET	/tasks	Get own tasks (filtered by status optional)
GET	/tasks/by-user/{email}	Get tasks by user (ADMIN only)
PUT	/tasks/{id}	Update task (admin or owner)
PATCH	/tasks/{id}/done	Mark task as done
PATCH	/tasks/{id}/in-progress	Mark task as in progress
DELETE	/tasks/{id}	Delete task (admin or owner)
 ```
### 🧪 Running Tests
```bash
./mvnw test
```
✅ Unit tests included for TaskService, UserService, and JWT logic.

### 📁 Project Structure
```csharp
com.saparbek.Task_Tracker
├── config             # Security, Swagger, Dotenv initializer
├── controller         # REST endpoints
├── dto                # Request/response models
├── exception          # Global error handling
├── model              # JPA entities & enums
├── repository         # Spring Data repositories
├── security           # JWT, custom auth, RBAC
├── service            # Business logic
```
### 📌 Author
### Saparbek Kozhanazar
Feel free to contribute, fork, or raise issues!

### 📄 License
This project is open-source and free to use under the MIT License.

---
Let me know if you'd like:
- a badge for build/test status
- example cURL requests for login + token use
- frontend suggestions (React/Vue) that match this API setup
