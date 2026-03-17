Here’s a polished **README.md** draft for your project, Abhishek. It’s structured to look professional and interview‑ready, highlighting architecture, setup, and usage.

---

# Microservices Mini System

A portfolio‑ready **Spring Boot + Dockerized microservices project** featuring user management and order processing with **MySQL databases**, **JWT authentication**, and **role‑based access control**.

---

## 🚀 Features
- **User Service**  
  - Register, login, and manage users  
  - JWT authentication with role‑based access (USER / ADMIN)  
  - Secure endpoints with Spring Security  

- **Order Service**  
  - Create and manage orders  
  - Ownership validation (users can only manage their own orders)  
  - Admin role with elevated privileges  

- **Databases**  
  - Separate MySQL containers for `user-db` and `order-db`  
  - Docker Compose orchestration  

- **Deployment**  
  - Dockerized services with `eclipse-temurin:21-jdk` base image  
  - `docker-compose.yml` for multi‑service setup  

---

## 🛠 Tech Stack
- **Java 21**  
- **Spring Boot** (REST APIs, Security, Validation)  
- **MySQL 8**  
- **Docker & Docker Compose**  
- **Maven** (build automation)  

---

## 📂 Project Structure
```
microservices-mini-system/
│── user-service/
│   ├── src/main/java/... (User APIs)
│   ├── target/user-service-0.0.1-SNAPSHOT.jar
│   └── Dockerfile
│
│── order-service/
│   ├── src/main/java/... (Order APIs)
│   ├── target/order-service-0.0.1-SNAPSHOT.jar
│   └── Dockerfile
│
│── docker-compose.yml
└── README.md
```

---

## ⚙️ Setup & Run

### 1. Build JARs
```bash
cd user-service
mvn clean package -DskipTests

cd ../order-service
mvn clean package -DskipTests
```

### 2. Start Services
```bash
docker compose up --build
```

### 3. Verify Containers
```bash
docker ps
```
Expected:
- `user-db` → localhost:3307  
- `order-db` → localhost:3308  
- `user-service` → localhost:8081  
- `order-service` → localhost:8082  

---

## 🔑 API Endpoints

### User Service (`localhost:8081`)
- `POST /auth/register` → Register new user  
- `POST /auth/login` → Login and receive JWT  
- `GET /users/me` → Get current user details  

### Order Service (`localhost:8082`)
- `POST /orders` → Create new order (requires JWT)  
- `GET /orders` → List user’s orders  
- `DELETE /orders/{id}` → Delete own order  

---

## 🧪 Testing Flows
1. Register users (e.g., Sam, Jane, Rohan).  
2. Login to get JWT tokens.  
3. Place orders with `Authorization: Bearer <token>`.  
4. Validate ownership and role restrictions.  

---

## 📖 Notes
- Databases are isolated per service.  
- Admin role can view/delete all orders.  
- Regular users can only manage their own orders.  




