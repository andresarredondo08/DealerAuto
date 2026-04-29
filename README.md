# 🏢 Dealer & Vehicle Inventory Module

## 📌 Overview

This project implements a **multi-tenant Inventory module** inside a **Modular Monolith architecture** using Spring Boot.

The system manages **Dealers** and their **Vehicles**, enforcing strict tenant isolation and supporting filtering, pagination, and role-based access control.

---

## 🧱 Architecture

The application follows **Clean Architecture principles**, separating responsibilities into:

```
inventory
 ├── dealer
 │    ├── domain
 │    ├── application
 │    ├── controller
 │    ├── repository
 │    └── mapper
 ├── vehicle
 │    ├── domain
 │    ├── application
 │    ├── controller
 │    ├── repository
 │    └── specification
 ├── admin
 │    ├── controller
 │    └── application
 ├── security
 │    ├── TenantFilter
 │    └── SecurityConfig
 └── shared
      └── exception
```

---

## 🧠 Key Concepts

### 🔹 Multi-Tenancy

Tenant isolation is enforced using:

* `X-Tenant-Id` HTTP header
* `TenantFilter` (OncePerRequestFilter)
* `ThreadLocal TenantContext`

Each request is scoped to a specific tenant.

---

### 🔹 Security

* Basic Authentication
* Role-based access control
* Admin endpoints restricted to `GLOBAL_ADMIN`

---

### 🔹 Dynamic Filtering

Implemented using **Spring Data JPA Specifications**

Supports:

* model
* status
* price range
* dealer subscription

---

## 🗄️ Data Model

### Dealer

* id (UUID)
* tenantId
* name
* email
* subscriptionType (BASIC | PREMIUM)

### Vehicle

* id (UUID)
* tenantId
* dealer (ManyToOne)
* model
* price
* status (AVAILABLE | SOLD)

---

## 🚀 API Endpoints

### Dealers

| Method | Endpoint      |
| ------ | ------------- |
| POST   | /dealers      |
| GET    | /dealers/{id} |
| GET    | /dealers      |
| PATCH  | /dealers/{id} |
| DELETE | /dealers/{id} |

---

### Vehicles

| Method | Endpoint       |
| ------ | -------------- |
| POST   | /vehicles      |
| GET    | /vehicles/{id} |
| GET    | /vehicles      |
| PATCH  | /vehicles/{id} |
| DELETE | /vehicles/{id} |

---

### 🔍 Filters

```
GET /vehicles?model=toyota
GET /vehicles?status=AVAILABLE
GET /vehicles?priceMin=10000&priceMax=30000
GET /vehicles?subscription=PREMIUM
```

---

### 🛠 Admin

```
GET /admin/dealers/countBySubscription
```

Returns:

```json
{
  "BASIC": 10,
  "PREMIUM": 5
}
```

👉 This endpoint returns **global counts across all tenants**

---

## 🔐 Authentication

Basic Auth credentials:

```
admin / admin123  → GLOBAL_ADMIN
user  / user123   → USER
```

---

## 🧪 How to Test

### 1. Create Dealer

```
POST /dealers
Header: X-Tenant-Id: tenant-a
```

---

### 2. Create Vehicle

```
POST /vehicles
Header: X-Tenant-Id: tenant-a
```

---

### 3. Filter by subscription

```
GET /vehicles?subscription=PREMIUM
```

---

## ✅ Acceptance Criteria

| Requirement                 | Status |
| --------------------------- | ------ |
| Missing tenant header → 400 | ✔      |
| Cross-tenant access blocked | ✔      |
| Filtering by subscription   | ✔      |
| Admin secured endpoint      | ✔      |

---

## 🧰 Tech Stack

* Java 21
* Spring Boot 3.5
* Spring Data JPA
* Spring Security
* MapStruct
* Lombok
* H2 Database

---

## ⚙️ Configuration

```
spring.datasource.url=jdbc:h2:mem:dealersdb
```

👉 Data is stored in-memory and reset on restart.

---

## 📊 API Documentation

Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

---

## 🧾 Notes

* Admin endpoints are not tenant-scoped
* All other endpoints enforce strict tenant isolation
* Specifications are used to avoid query explosion

---

## 👨‍💻 Author

Andres Arredondo
