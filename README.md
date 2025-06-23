# Digital Banking API system

This is a Spring Boot project that handles customer accounts, money transfers, and transaction history logs.

## Features

- Create Customer Account
- Transfer Money between Accounts
- Check Account balance
- Transaction History Logging

## Test Types

- Unit Tests (with Mockito)
- Integration Tests (with Postgres + SpringBootTest)

## Technologies required
The fully fledged server uses the following:

- Spring Boot 3.5.0
- JPA (Hibernate)
- PostgreSQL
- Lombok 
- MapStruct 
- Jakarta (validation-api)
- Swagger (Expose your doc api)

## Building the project
You will need:

*	Java JDK 17 or higher
*	Maven 3.9.1 or higher

Clone the project and use Maven to build the server

	$ mvn clean install

Swagger UI access

    http://localhost:9099/swagger-ui/index.html#/
