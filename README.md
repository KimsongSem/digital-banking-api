# 💳 Digital Banking API

A secure, scalable, and modular RESTful API built with Spring Boot 3.5 for digital banking operations. It supports account creation, balance tracking, fund transfers, and customer management with robust error handling.

---

## 📚 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [Environment Configuration](#-environment-configuration)
- [API Documentation](#-api-documentation)
- [Usage Examples](#-usage-examples)
- [Testing](#-testing)

---

## 🚀 Features

- ✅ Customer Account create  
- ✅ Balance inquiry and transaction history  
- ✅ Transfers  
- ✅ Global exception handling with custom errors  
- ✅ Swagger/OpenAPI integration for API documentation  
- ✅ Database integration with Spring Data JPA

---

## 🛠 Tech Stack

| Layer           | Technology                |
|----------------|---------------------------|
| Language        | Java 17+                  |
| Framework       | Spring Boot 3.5           |
| Data Access     | Spring Data JPA           |
| Database        | PostgreSQL                |
| Docs            | Swagger (Springdoc)       |
| Build Tool      | Maven                     |
| Testing         | JUnit 5, Mockito          |

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/kimsong/digital_banking/
│   │   ├── config/
│   │   ├── constants/
│   │   ├── controllers/
│   │   ├── dtos/
│   │   ├── exceptions/
│   │   ├── generators/
│   │   ├── mappers/
│   │   ├── models/
│   │   ├── repositories/
│   │   ├── services/
│   │   ├── shared/
│   │   ├── specifications/
│   │   ├── utils/
│   └── resources/
│       ├── application.properties
│       └── application-dev.properties
│       └── application-uat.properties
│       └── logback.xml
└── test/
    └── ...
```

---

## 🧰 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/KimsongSem/digital-banking-api.git
cd digital-banking-api
```

### 2. Build the Project

```bash
./mvnw clean install
```

### 3. Configure the Database

Create a PostgreSQL database named `digital_banking_db`.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/digital_banking_db
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=none
```

### 4. Run the Application

```bash
./mvnw spring-boot:run
```

---

## 📄 API Documentation

Access Swagger UI once the app is running:

```
http://localhost:9099/swagger-ui/index.html
```

OpenAPI docs are auto-generated from annotations in your controllers and DTOs.

---

## 📦 Usage Examples

### Create a Bank Account

`POST /api/accounts/create`

```json
{
  "customer": {
    "firstName": "Kimsong",
    "lastName": "SEM",
    "gender": "M",
    "phone": "0962192240",
    "dateOfBirth": "2000-04-13T00:00:00.000",
    "nationalId": "123456",
    "address": "Phnom Penh"
  },
  "accountType":"SAVINGS",
  "currency":"USD",
  "balance": 100
}
```

### Check Account Balance

`GET /api/accounts/{accountId}/balance`

### Transfer Money

`POST /api/transfers`

```json
{
  "fromAccountNumber": 100000001,
  "toAccountNumber": 100000002,
  "amount": 1000,
  "purpose": "Testing transfer"
}
```

### Check Account Balance

`GET /api/transactions/getAllHistory`

## 🧪 Testing

Run all unit and integration tests using:

```bash
./mvnw test
```

Test cases are located under the `src/test` directory and include coverage for:

- Controllers with mocks
- Services with mocks
- Integration with repositories
- 
---

## 🙋‍♂️ Contact

Created and maintained by **Kimsong Sem**  
📧 Email: [kimsongkd@gmail.com]  
🔗 GitHub: [github.com/KimsongSem](https://github.com/KimsongSem)

---

> “Banking APIs should be safe, scalable, and simple—this project is a step in that direction.” – _Kimsong_
