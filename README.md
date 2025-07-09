
# 🚚 Dispatch Load Balancer - Spring Boot Application

This is a backend service that **optimally assigns delivery orders to a fleet of vehicles**, considering:
- Package weight
- Vehicle capacity
- Location proximity using Haversine formula
- Order priority (HIGH > MEDIUM > LOW)

---

## 📦 Project Structure

```

src
├── main
│   ├── java
│   │   └── com.loadBalancer.app
│   │       ├── controller         # REST APIs
│   │       ├── dto                # Request/Response DTOs
│   │       ├── entity             # JPA Entities
│   │       ├── exception          # Custom Exceptions
│   │       ├── repository         # Spring Data Repositories
│   │       ├── service            # Interfaces
│   │       ├── service.impl       # Business Logic
│   │       └── util               # Utility (Distance calculation)
│   └── resources
│       ├── application.properties
│       └── static/outputs         # Screenshots: DB + Postman responses
└── test
└── java/com.loadBalancer.app # JUnit & Mockito Test Cases

````

---

## ⚙️ Tech Stack

| Layer         | Tools/Tech                                |
|---------------|-------------------------------------------|
| Language      | Java 21                                   |
| Framework     | Spring Boot 3.5.3                         |
| Web           | Spring Web (REST)                         |
| Validation    | Jakarta Validation                        |
| Database      | MySQL                                     |
| ORM           | Spring Data JPA + Hibernate               |
| Mapping       | ModelMapper                               |
| Testing       | JUnit 5, Mockito, postman                 |


---

## 📑 API Endpoints

### ➕ Add Orders
```http
POST /api/dispatch/orders
````

```json
{
  "orders": [
    {
      "orderId": "ORD001",
      "latitude": 28.6139,
      "longitude": 77.2090,
      "address": "Connaught Place, Delhi, India",
      "packageWeight": 15,
      "priority": "HIGH"
    }
  ]
}
```

---

### ➕ Add Vehicles

```http
POST /api/dispatch/vehicles
```

```json
{
  "vehicles": [
    {
      "vehicleId": "VEH001",
      "capacity": 50,
      "currentLatitude": 28.6139,
      "currentLongitude": 77.2088,
      "currentAddress": "Karol Bagh, Delhi, India"
    }
  ]
}
```

---

### 📤 Get Dispatch Plan

```http
GET /api/dispatch/plan
```

#### Response:

```json
[
  {
    "vehicleId": "VEH001",
    "totalLoad": 40,
    "totalDistance": "3.2 km",
    "assignedOrders": [ ... ]
  }
]
```

---

## 📂 Database Schema (MySQL)

```sql
Table: orders
- order_id (PK)
- latitude
- longitude
- address
- package_weight
- priority (ENUM)
- assigned (boolean)

Table: vehicles
- vehicle_id (PK)
- capacity
- current_latitude
- current_longitude
- current_address
```

---

## 🚀 How to Run the Project

### ✅ Prerequisites:

* Java 21
* Maven
* MySQL running on port 3306

### 🔧 Setup

1. Clone the repo:

   ```bash
   git clone https://github.com/your-repo/dispatch-load-balancer.git
   cd dispatch-load-balancer
   ```

2. Update MySQL credentials in `src/main/resources/application.properties`:

   ```properties
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.datasource.url=jdbc:mysql://localhost:3306/loadbalancer
   ```

3. Build & run:

   ```bash
   mvn spring-boot:run
   ```

---

## 📘 Swagger API Docs

* Available at:

  ```
  http://localhost:8080/swagger-ui/index.html
  ```

---

## 🧪 Testing

Run JUnit tests:

```bash
mvn test
```

Includes:

* Positive tests for order/vehicle additions and dispatch
* Edge cases (empty input, overcapacity, etc.)
* Mockito-based service layer mocks

---

## 🧰 Tools Used

* **Spring Boot**
* **Hibernate + JPA**
* **ModelMapper**
* **Jakarta Bean Validation**
* **JUnit 5**
* **Mockito**
* **Postman**
---

## 📸 Output Screenshots

Located under:

```
src/main/resources/static/
```

* Postman results
* MySQL table snapshots

---

## ✍️ Author

Rajesh K
Backend Java Developer, 
email : rajesh.kofficialmail@gmail.com

---

