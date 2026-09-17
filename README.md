# RHB Spring Boot Technical Assignment

A RESTful backend application developed using **Java 17** and **Spring Boot** as part of the RHB technical assessment.

The application implements a Customer and Order Management System and demonstrates REST API development, layered architecture, CRUD operations, request validation, pagination, relational database operations, JPQL JOIN queries, centralized exception handling, Aspect-Oriented Programming (AOP), external REST API integration, unit testing, Swagger/OpenAPI documentation, Postman API testing, and JWT-based authentication using Spring Security.

---

# 1. Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Spring Boot | Application framework |
| Spring Web MVC | REST API development |
| Spring Data JPA | Persistence and database operations |
| Hibernate | ORM implementation |
| H2 Database | In-memory relational database |
| Spring Security | Authentication and API security |
| OAuth2 Resource Server | JWT Bearer token validation |
| JWT | Stateless authentication |
| BCrypt | Secure password hashing |
| Spring AOP / AspectJ | Request and response logging |
| Jakarta Validation | Request validation |
| RestClient | External REST API communication |
| Maven | Dependency management and build |
| JUnit 5 | Unit testing |
| Mockito | Mocking dependencies in unit tests |
| Swagger / OpenAPI | Interactive API documentation |
| Postman | REST API testing |
| Lombok | Reduction of Java boilerplate code |

---

# 2. Project Overview

The application implements a **Customer and Order Management System** secured using JWT authentication.

Users can register and authenticate through the Authentication API. After successful authentication, the server generates a JWT access token that can be used to access protected endpoints.

A customer can be:

- Created
- Retrieved
- Updated
- Deleted
- Searched by name
- Retrieved using pagination

An order belongs to a customer. This relationship demonstrates relational database mapping and JOIN operations using Spring Data JPA and JPQL.

The application also integrates with an external REST API using Spring's `RestClient`.

The primary business relationship is:

```text
Customer
   |
   | 1
   |
   | *
 Order
```

A single customer can have multiple orders, while each order belongs to one customer.

Authentication is handled separately using an application user:

```text
AppUser
   |
   | username/password
   v
Spring Security
   |
   v
JWT
```

---

# 3. Features

The application provides the following functionality:

## Customer Management

- Create a customer
- Retrieve a customer by ID
- Update an existing customer
- Delete a customer
- Search customers by name
- Paginate customer search results

## Order Management

- Create an order for a customer
- Retrieve an order by ID
- Retrieve orders belonging to a customer
- Execute a JPQL JOIN between Customer and Order

## Security

- Register application users
- Store users in the H2 database
- Encode passwords using BCrypt
- Authenticate users using username and password
- Generate JWT access tokens
- Validate JWT Bearer tokens
- Protect REST endpoints using Spring Security
- Use stateless authentication

## Additional Features

- Validate incoming API requests
- Handle application exceptions centrally
- Return appropriate HTTP status codes
- Log incoming requests and outgoing responses using AOP
- Call an external REST API
- Store application data using H2
- Unit test service-layer business logic using JUnit and Mockito
- Document APIs using Swagger/OpenAPI
- Authenticate through Swagger using JWT
- Test APIs using a provided Postman collection

---

# 4. Architecture

The project follows a layered Spring Boot architecture.

```text
                         Client
                           |
                           | HTTP Request
                           v
                  +------------------+
                  | Spring Security  |
                  +------------------+
                           |
                           | JWT Validation
                           v
                  +------------------+
                  |    Controller    |
                  +------------------+
                           |
                           v
                  +------------------+
                  |     Service      |
                  +------------------+
                           |
                           v
                  +------------------+
                  |    Repository    |
                  +------------------+
                           |
                           v
                  +------------------+
                  |   H2 Database    |
                  +------------------+
```

The responsibilities of each layer are separated to improve maintainability, security, and testability.

## Controller Layer

Responsible for:

- Receiving HTTP requests
- Mapping request parameters and bodies
- Triggering request validation
- Calling services
- Returning HTTP responses

## Service Layer

Responsible for:

- Business logic
- Entity creation and modification
- DTO/entity conversion
- Authentication logic
- JWT generation
- Coordinating repository operations
- Validating resource existence
- External API communication

## Repository Layer

Responsible for:

- Database communication
- CRUD operations
- Spring Data JPA queries
- Pagination
- JPQL JOIN operations
- User lookup for authentication

## DTO Layer

DTOs separate the REST API contract from persistence entities.

```text
HTTP JSON
    |
    v
Request DTO
    |
    v
Controller
    |
    v
Service
    |
    v
Entity
    |
    v
Repository
```

Responses follow the reverse flow:

```text
Database
    |
    v
Entity
    |
    v
Service
    |
    v
Response DTO
    |
    v
JSON Response
```

---

# 5. Project Structure

```text
src
├── main
│   ├── java
│   │   └── com
│   │       └── rhb
│   │           └── assignment
│   │               │
│   │               ├── AssignmentApplication.java
│   │               │
│   │               ├── aspect
│   │               │   └── LoggingAspect.java
│   │               │
│   │               ├── config
│   │               │   ├── OpenApiConfig.java
│   │               │   └── SecurityConfig.java
│   │               │
│   │               ├── controller
│   │               │   ├── AuthenticationController.java
│   │               │   ├── CustomerController.java
│   │               │   ├── OrderController.java
│   │               │   └── ExternalApiController.java
│   │               │
│   │               ├── dto
│   │               │   ├── RegisterRequest.java
│   │               │   ├── LoginRequest.java
│   │               │   ├── LoginResponse.java
│   │               │   ├── CustomerRequest.java
│   │               │   ├── CustomerResponse.java
│   │               │   ├── OrderRequest.java
│   │               │   ├── OrderResponse.java
│   │               │   └── ErrorResponse.java
│   │               │
│   │               ├── entity
│   │               │   ├── AppUser.java
│   │               │   ├── Customer.java
│   │               │   └── Order.java
│   │               │
│   │               ├── exception
│   │               │   ├── ResourceNotFoundException.java
│   │               │   └── GlobalExceptionHandler.java
│   │               │
│   │               ├── repository
│   │               │   ├── UserRepository.java
│   │               │   ├── CustomerRepository.java
│   │               │   └── OrderRepository.java
│   │               │
│   │               └── service
│   │                   ├── AuthenticationService.java
│   │                   ├── JwtService.java
│   │                   ├── CustomerService.java
│   │                   ├── OrderService.java
│   │                   └── ExternalApiService.java
│   │
│   └── resources
│       └── application.properties
│
└── test
    └── java
        └── com
            └── rhb
                └── assignment
                    └── service
                        ├── AuthenticationServiceTest.java
                        ├── CustomerServiceTest.java
                        └── OrderServiceTest.java

postman
└── RHB-Assignment.postman_collection.json

pom.xml
README.md
```

---

# 6. Prerequisites

Before running the application, make sure Java 17 is installed.

Verify:

```bash
java -version
```

The project contains the Maven Wrapper, so Maven does not need to be installed globally.

macOS/Linux:

```bash
./mvnw -version
```

Windows:

```bash
mvnw.cmd -version
```

---

# 7. Building the Application

Clone the repository:

```bash
git clone <repository-url>
```

Navigate into the project:

```bash
cd assignment
```

Run:

```bash
./mvnw clean install
```

This will:

1. Clean previous build files
2. Compile the application
3. Execute unit tests
4. Package the application

A successful build should finish with:

```text
BUILD SUCCESS
```

---

# 8. Running the Application

Run:

```bash
./mvnw spring-boot:run
```

Alternatively:

```bash
./mvnw clean package
```

Then:

```bash
java -jar target/assignment-0.0.1-SNAPSHOT.jar
```

The application is available at:

```text
http://localhost:8080
```

---

# 9. Authentication and JWT Security

The application uses **Spring Security** with JWT-based stateless authentication.

The authentication process consists of:

```text
Registration
     |
     v
BCrypt Password Encoding
     |
     v
USERS Table


Login
     |
     v
AuthenticationManager
     |
     v
UserDetailsService
     |
     v
UserRepository
     |
     v
Password Verification
     |
     v
JWT Generation
```

The JWT is then used for protected requests:

```text
Client
   |
   | Authorization: Bearer <JWT>
   v
Spring Security
   |
   v
JWT Decoder
   |
   +-------- Invalid --------> 401 Unauthorized
   |
   +-------- Valid
               |
               v
           Controller
```

The application does not maintain server-side authentication sessions.

---

# 10. Register User

## Endpoint

```http
POST /api/auth/register
```

This endpoint is public.

## Example Request

```json
{
    "username": "uthaya",
    "password": "Password123"
}
```

## HTTP Status

```text
201 Created
```

Before the user is stored, the password is encoded using BCrypt.

The raw password is never intentionally persisted directly.

---

# 11. Login

## Endpoint

```http
POST /api/auth/login
```

This endpoint is public.

## Example Request

```json
{
    "username": "uthaya",
    "password": "Password123"
}
```

## Example Response

```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "type": "Bearer",
    "expiresIn": 3600
}
```

The token is valid for:

```text
3600 seconds / 1 hour
```

Protected API requests must send:

```http
Authorization: Bearer <JWT_TOKEN>
```

For example:

```http
GET /api/customers/1
Authorization: Bearer eyJ...
```

Without a valid JWT, a protected endpoint returns:

```text
401 Unauthorized
```

---

# 12. Customer API

Customer endpoints require JWT authentication.

## Create Customer

### Endpoint

```http
POST /api/customers
```

### Example Request

```json
{
    "name": "John Tan",
    "email": "john@gmail.com",
    "phone": "0123456789"
}
```

### Example Response

```json
{
    "id": 1,
    "name": "John Tan",
    "email": "john@gmail.com",
    "phone": "0123456789"
}
```

### HTTP Status

```text
201 Created
```

---

## Get Customer by ID

```http
GET /api/customers/{id}
```

Example:

```http
GET /api/customers/1
```

Example response:

```json
{
    "id": 1,
    "name": "John Tan",
    "email": "john@gmail.com",
    "phone": "0123456789"
}
```

### HTTP Status

```text
200 OK
```

---

## Update Customer

```http
PUT /api/customers/{id}
```

Example:

```http
PUT /api/customers/1
```

Request:

```json
{
    "name": "John Tan Updated",
    "email": "john.updated@gmail.com",
    "phone": "0198765432"
}
```

### HTTP Status

```text
200 OK
```

---

## Delete Customer

```http
DELETE /api/customers/{id}
```

Example:

```http
DELETE /api/customers/1
```

### HTTP Status

```text
204 No Content
```

---

# 13. Search and Pagination

Customers can be searched by name with pagination.

```http
GET /api/customers/search
```

Parameters:

| Parameter | Description | Example |
|---|---|---|
| `name` | Customer name search value | John |
| `page` | Page number starting from 0 | 0 |
| `size` | Number of records per page | 10 |

Example:

```http
GET /api/customers/search?name=John&page=0&size=10
```

Spring Data's `Pageable` abstraction performs database-level pagination.

The repository uses a derived query similar to:

```java
Page<Customer> findByNameContainingIgnoreCase(
        String name,
        Pageable pageable
);
```

This provides partial, case-insensitive searching while limiting the number of records returned.

---

# 14. Order API

Order endpoints require JWT authentication.

An order must belong to an existing customer.

## Create Order

```http
POST /api/orders
```

Request:

```json
{
    "productName": "Laptop",
    "amount": 3500.00,
    "status": "PENDING",
    "customerId": 1
}
```

Before creating the order, the application verifies that the customer exists.

Example response:

```json
{
    "id": 1,
    "productName": "Laptop",
    "amount": 3500.00,
    "status": "PENDING",
    "customerId": 1,
    "customerName": "John Tan"
}
```

### HTTP Status

```text
201 Created
```

---

## Get Order by ID

```http
GET /api/orders/{id}
```

Example:

```http
GET /api/orders/1
```

---

## Get Orders by Customer

```http
GET /api/orders/customer/{customerId}
```

Example:

```http
GET /api/orders/customer/1
```

This endpoint demonstrates querying data across the Customer and Order relationship.

---

# 15. Database Design

The application uses three main tables:

```text
USERS
-----
id
username
password
role


CUSTOMERS
---------
id
name
email
phone


ORDERS
------
id
product_name
amount
status
customer_id
```

## User Data

Registered application users are stored in `USERS`.

Example:

```text
ID | USERNAME | PASSWORD   | ROLE
1  | uthaya   | $2a$10$... | USER
```

The password stored in the database is a BCrypt hash rather than the raw password.

## Customer and Order Relationship

```text
CUSTOMERS
    |
    | id
    |
    | 1
    |
    | *
    |
ORDERS
    |
    | customer_id
```

The `Order` entity contains a relationship similar to:

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "customer_id", nullable = false)
private Customer customer;
```

Multiple orders can therefore belong to one customer.

---

# 16. JPQL JOIN Query

The project demonstrates an explicit JPQL JOIN between `Order` and `Customer`.

```java
@Query("""
        SELECT o
        FROM Order o
        JOIN o.customer c
        WHERE c.id = :customerId
        """)
List<Order> findOrdersByCustomer(
        @Param("customerId") Long customerId
);
```

JPQL operates on Java entities and their mapped relationships rather than directly querying database table names.

```text
Order
   |
   | o.customer
   v
Customer
```

The query retrieves all orders associated with a specified customer.

---

# 17. H2 In-Memory Database

The application uses H2 as an in-memory relational database.

Example configuration:

```properties
spring.datasource.url=jdbc:h2:mem:assignmentdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

## H2 Console

While the application is running:

```text
http://localhost:8080/h2-console
```

Use:

```text
JDBC URL: jdbc:h2:mem:assignmentdb
Username: sa
Password:
```

The password is empty unless the configuration is changed.

You can inspect the tables using:

```sql
SELECT * FROM USERS;

SELECT * FROM CUSTOMERS;

SELECT * FROM ORDERS;
```

> Because H2 is an in-memory database, application data is reset when the application restarts.

---

# 18. Request Validation

Incoming request DTOs use Jakarta Bean Validation.

Example:

```java
public record CustomerRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        String phone

) {}
```

Controllers trigger validation using:

```java
@Valid @RequestBody CustomerRequest request
```

An invalid request such as:

```json
{
    "name": "",
    "email": "invalid-email",
    "phone": "0123456789"
}
```

returns:

```text
400 Bad Request
```

Authentication request DTOs also use Jakarta Validation.

---

# 19. Exception Handling

Centralized exception handling is implemented using:

```java
@RestControllerAdvice
```

A custom:

```text
ResourceNotFoundException
```

is thrown when a requested business resource cannot be found.

For example:

```http
GET /api/customers/999999
```

can return:

```json
{
    "timestamp": "2026-09-17T20:15:23",
    "status": 404,
    "error": "Not Found",
    "message": "Customer not found with id: 999999",
    "path": "/api/customers/999999"
}
```

with:

```text
404 Not Found
```

This keeps exception handling separate from controller and service business logic.

---

# 20. AOP Request and Response Logging

The project uses **Spring AOP / AspectJ** for centralized request and response logging.

Logging is a cross-cutting concern because the same behavior is required across multiple controllers.

Instead of duplicating logging code in each controller, an Aspect intercepts controller execution.

Example pointcut:

```java
@Around("execution(* com.rhb.assignment.controller..*(..))")
```

Flow:

```text
HTTP Request
      |
      v
LoggingAspect
      |
      | Log Request
      v
Controller
      |
      v
Service
      |
      v
Repository
      |
      v
Database
      |
      v
Controller Response
      |
      v
LoggingAspect
      |
      | Log Response
      v
Client
```

The intercepted method is executed using:

```java
joinPoint.proceed();
```

This centralizes logging while keeping controllers focused on request handling.

---

# 21. External REST API Integration

The application demonstrates communication with an external REST service.

The project uses **JSONPlaceholder** through a dedicated:

```text
ExternalApiService
```

using Spring's:

```text
RestClient
```

The application exposes:

```http
GET /api/external/users/{id}
```

Example:

```http
GET /api/external/users/1
```

Flow:

```text
Client
   |
   v
Spring Security
   |
   v
ExternalApiController
   |
   v
ExternalApiService
   |
   v
Spring RestClient
   |
   v
External REST API
   |
   v
JSON Response
```

Keeping external communication in the service layer separates integration logic from controller logic.

---

# 22. Swagger / OpenAPI Documentation

Swagger/OpenAPI documentation is included for exploring and testing the REST API.

Start the application and open:

```text
http://localhost:8080/swagger-ui.html
```

The generated OpenAPI specification is available at:

```text
http://localhost:8080/v3/api-docs
```

Swagger can be used to:

- View endpoints
- Inspect parameters
- Inspect request and response schemas
- Register a user
- Login
- Authenticate using JWT
- Execute protected requests

---

## JWT Authentication in Swagger

First register a user:

```http
POST /api/auth/register
```

Then login:

```http
POST /api/auth/login
```

Copy the returned:

```json
{
    "token": "eyJ..."
}
```

Click:

```text
Authorize
```

in Swagger UI.

Paste only the JWT value:

```text
eyJ...
```

Swagger automatically sends:

```http
Authorization: Bearer eyJ...
```

with protected requests.

You can verify security by calling a protected endpoint without authorization:

```http
GET /api/customers/1
```

Expected:

```text
401 Unauthorized
```

After authorization with a valid JWT, the same endpoint can be accessed.

---

# 23. Unit Testing

Service-layer unit tests use:

- JUnit 5
- Mockito

Run:

```bash
./mvnw test
```

## Customer Tests

Example scenarios include:

- Create customer successfully
- Retrieve an existing customer
- Handle customer not found

## Order Tests

Example scenarios include:

- Create an order
- Retrieve an order
- Retrieve orders by customer
- Handle missing resources

## Authentication Tests

Authentication tests cover scenarios such as:

- Successful user registration
- Password encoding before persistence
- Duplicate username rejection
- Successful authentication
- JWT generation after successful authentication
- Invalid credential handling
- Ensuring a JWT is not generated after failed authentication

Dependencies are mocked during unit testing.

Example:

```java
@Mock
private UserRepository userRepository;

@Mock
private PasswordEncoder passwordEncoder;

@Mock
private AuthenticationManager authenticationManager;

@Mock
private JwtService jwtService;

@InjectMocks
private AuthenticationService authenticationService;
```

This isolates `AuthenticationService` from the actual database and Spring Security infrastructure.

---

# 24. Running Tests

Run all tests:

```bash
./mvnw clean test
```

Expected:

```text
BUILD SUCCESS
```

A complete build can be performed with:

```bash
./mvnw clean install
```

---

# 25. Postman Collection

A Postman collection is included:

```text
postman/RHB-Assignment.postman_collection.json
```

Import it using:

```text
Postman
   |
   v
Import
   |
   v
Files
   |
   v
RHB-Assignment.postman_collection.json
```

The collection uses the following variables:

| Variable | Purpose |
|---|---|
| `baseUrl` | Application base URL |
| `jwtToken` | JWT returned after login |
| `customerId` | ID of the created customer |
| `orderId` | ID of the created order |

Default:

```text
{{baseUrl}} = http://localhost:8080
```

The collection structure is:

```text
RHB Spring Boot Assignment
│
├── Authentication
│   ├── Register User
│   └── Login User
│
├── Customers
│   ├── Create Customer
│   ├── Get Customer By ID
│   ├── Update Customer
│   ├── Delete Customer
│   └── Search Customers With Pagination
│
├── Orders
│   ├── Create Order
│   ├── Get Order By ID
│   └── Get Orders By Customer
│
├── External API
│   └── Get External User
│
├── Security Tests
│   └── Protected Endpoint Without JWT - 401
│
└── Error Scenarios
    ├── Customer Not Found - 404
    └── Invalid Customer - 400
```

---

# 26. Automatic JWT Handling in Postman

The Login request automatically extracts the JWT from the response:

```javascript
const response = pm.response.json();

if (response.token) {
    pm.collectionVariables.set(
        "jwtToken",
        response.token
    );
}
```

The collection then uses:

```text
{{jwtToken}}
```

for Bearer authentication.

Protected requests automatically send:

```http
Authorization: Bearer {{jwtToken}}
```

The Create Customer request can also save the returned customer ID:

```javascript
const response = pm.response.json();

if (response.id) {
    pm.collectionVariables.set(
        "customerId",
        response.id
    );
}
```

Similarly, Create Order can store:

```text
{{orderId}}
```

This allows the collection to be executed sequentially without manually copying IDs or JWTs between requests.

---

# 27. Recommended Postman Testing Order

Because H2 is an in-memory database, begin with registration after restarting the application.

Recommended sequence:

```text
1. Register User
        |
        v
2. Login User
        |
        | JWT automatically stored
        v
3. Create Customer
        |
        | customerId automatically stored
        v
4. Get Customer By ID
        |
        v
5. Search Customers
        |
        v
6. Create Order
        |
        | orderId automatically stored
        v
7. Get Order By ID
        |
        v
8. Get Orders By Customer
        |
        v
9. Update Customer
        |
        v
10. Test Error Scenarios
        |
        v
11. Delete Customer
```

An order must be created after its associated customer because `customerId` must reference an existing customer.

If registration is attempted twice without restarting the in-memory database, the duplicate username should be rejected.

---

# 28. HTTP Status Codes

| Status | Meaning | Example |
|---|---|---|
| `200 OK` | Successful retrieval/update/login | Login or Get Customer |
| `201 Created` | Resource successfully created | Register User / Create Customer |
| `204 No Content` | Resource successfully deleted | Delete Customer |
| `400 Bad Request` | Request validation failed | Invalid email |
| `401 Unauthorized` | Authentication required or JWT invalid | Protected API without JWT |
| `404 Not Found` | Requested resource does not exist | Invalid customer ID |

---

# 29. Security Design

## Database-Backed Authentication

Users are not hardcoded in the security configuration.

Spring Security retrieves registered users from:

```text
UserRepository
      |
      v
USERS
```

The authentication flow is:

```text
AuthenticationManager
        |
        v
UserDetailsService
        |
        v
UserRepository
        |
        v
AppUser
```

---

## BCrypt Password Encoding

Passwords are encoded before persistence:

```java
passwordEncoder.encode(request.password())
```

The database therefore stores a BCrypt hash rather than the original password.

---

## JWT-Based Stateless Authentication

After successful authentication, the application generates a signed JWT.

The client supplies the token on subsequent requests:

```http
Authorization: Bearer <JWT_TOKEN>
```

Spring Security validates the token before allowing access to protected endpoints.

The server does not need to maintain an authenticated HTTP session for each client.

---

## Public Endpoints

Authentication endpoints are publicly accessible:

```text
/api/auth/**
```

Swagger/OpenAPI endpoints are also publicly accessible for API documentation.

The H2 console is accessible for local assignment/testing purposes.

---

## Protected Endpoints

Application business endpoints require authentication, including:

```text
/api/customers/**
/api/orders/**
```

and other endpoints covered by the application's authenticated security policy.

---

# 30. Other Design Decisions

## Constructor Injection

Dependencies are injected through constructors.

Example:

```java
private final CustomerRepository customerRepository;

public CustomerService(
        CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
}
```

Benefits include:

- Dependencies are explicit
- Dependencies can be `final`
- Classes are easier to unit test
- Field injection is avoided
- Spring automatically injects a single constructor

---

## DTOs Instead of Exposing Entities

JPA entities are not directly exposed through the REST API.

Instead:

```text
CustomerRequest
CustomerResponse
OrderRequest
OrderResponse
LoginRequest
LoginResponse
RegisterRequest
```

act as API contracts.

This separates:

```text
Persistence Model
       from
REST API Model
```

---

## Centralized Exception Handling

Exceptions are handled using:

```java
@RestControllerAdvice
```

instead of repetitive `try/catch` blocks in controllers.

---

## AOP for Logging

Logging is implemented as a cross-cutting concern using Spring AOP.

This avoids duplicating request/response logging code across controllers.

---

## Repository Abstraction

Repositories extend:

```java
JpaRepository<Entity, ID>
```

which provides operations such as:

```text
save()
findById()
findAll()
delete()
deleteById()
existsById()
```

without repetitive DAO implementation code.

---

# 31. Assignment Requirements Coverage

## Core Requirements

| Requirement | Implementation |
|---|---|
| Java 17 | Java 17 project configuration |
| Maven | Maven + Maven Wrapper |
| Spring Boot | Main application framework |
| GET API | Customer and Order retrieval |
| POST API | Customer and Order creation |
| PUT API | Customer update |
| DELETE API | Customer deletion |
| Search | Customer name search |
| Pagination | Spring Data `Pageable` |
| In-memory database | H2 |
| Two related tables | Customer + Order |
| JOIN query | JPQL Customer/Order JOIN |
| Request validation | Jakarta Validation |
| Exception handling | `@RestControllerAdvice` |
| Request/response logging | Spring AOP / AspectJ |
| External API call | Spring `RestClient` |
| Unit testing | JUnit 5 + Mockito |
| API documentation | Swagger/OpenAPI |
| API test collection | Postman collection |

## Additional Security Enhancements

| Enhancement | Implementation |
|---|---|
| Application security | Spring Security |
| User registration | Database-backed registration |
| User persistence | `AppUser` + `UserRepository` |
| Password security | BCrypt password hashing |
| Authentication | Username/password authentication |
| Access token | JWT |
| API protection | JWT Bearer authentication |
| Stateless security | OAuth2 Resource Server |
| Swagger security | Bearer JWT Authorize support |
| Postman authentication | Automatic JWT storage and reuse |
| Authentication testing | JUnit + Mockito |

JWT authentication is implemented as an additional security enhancement on top of the core assignment requirements.

---

# 32. Quick Start

Clone the project:

```bash
git clone <repository-url>
cd assignment
```

Run tests:

```bash
./mvnw clean test
```

Start:

```bash
./mvnw spring-boot:run
```

Open Swagger:

```text
http://localhost:8080/swagger-ui.html
```

Then:

```text
1. POST /api/auth/register
2. POST /api/auth/login
3. Copy JWT
4. Click Swagger Authorize
5. Enter JWT
6. Test Customer and Order APIs
```

Alternatively, import:

```text
postman/RHB-Assignment.postman_collection.json
```

into Postman and execute the requests in the recommended order.

---

# 33. API Testing Summary

The complete application flow can be demonstrated as:

```text
Register User
      |
      v
BCrypt Password
      |
      v
USERS Table
      |
      v
Login
      |
      v
JWT
      |
      v
Authenticated Request
      |
      v
Spring Security
      |
      v
Controller
      |
      v
Service
      |
      v
Repository
      |
      v
H2 Database
```

This project demonstrates both REST API development and the integration of common backend application concerns including persistence, validation, authentication, authorization, exception handling, logging, external service communication, API documentation, and automated testing.

---

# 34. Author

**Uthayasurian Salavamani**

Technical Assignment – Java / Spring Boot

---

# License

This project was developed for technical assessment and demonstration purposes.