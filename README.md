# 🛒 E-Commerce RESTful API

> A production-grade, enterprise-standard RESTful API for an E-Commerce platform.  
> Built with **Java 17**, **Spring Boot 3**, **Spring Security**, **JWT**, and **MySQL**.

---

## 📌 Highlights

| Category | Detail |
|---|---|
| **Language** | Java 17 (Pure — no Lombok, no code generation) |
| **Framework** | Spring Boot 3.2.5 |
| **Security** | JWT Authentication + Role-Based Access Control |
| **Database** | MySQL 8.0 with JPA/Hibernate |
| **API Docs** | Swagger UI (SpringDoc OpenAPI) |
| **Containerization** | Docker + Docker Compose |
| **Architecture** | Layered: `Client → DTO Request → Controller → Service → Repository → DB → Entity → Mapper → DTO Response → Client` |

---

## ✨ Key Features

### 🔐 Authentication & Authorization
- Stateless JWT-based authentication (Login / Register)
- Role-Based Access Control: `ROLE_ADMIN` and `ROLE_USER`
- BCrypt password hashing
- Custom `JwtAuthenticationFilter` for request validation
- Global CORS configuration

### 🛍️ Product Management (Admin)
- Full CRUD operations for Products and Categories
- **Soft Delete**: Products and Categories are never permanently removed — marked as `deleted = true` via Hibernate `@SQLDelete` and `@Where`
- **Advanced Search & Filter**: Dynamic queries using JPA Specification API
  - Filter by: product name, category, min price, max price
- Pagination and multi-field sorting

### 🛒 Shopping Cart
- Add / Remove items from cart
- Automatic stock quantity validation
- Cart is auto-created on first item addition

### 📦 Order Management
- Checkout flow: validates stock → calculates total → creates order → deducts inventory → clears cart
- Order history retrieval per user
- Admin can update order status (`PENDING` → `PAID` → `SHIPPED` → `DELIVERED` → `CANCELLED`)

### 🏗️ Enterprise Patterns Applied
- **Layered Architecture**: Controller → Service (Interface + Impl) → Repository
- **DTO Pattern**: Separate Request/Response DTOs — entities are never exposed to the client
- **Builder Pattern**: Custom type-safe builders for all JPA entities with inheritance support
- **Global Exception Handling**: Centralized `@RestControllerAdvice` with standardized `ApiResponse` wrapper
- **Bean Validation**: Dual-layer validation at both DTO level (API input) and Entity level (data integrity)
- **Constant Extraction**: All API response messages centralized in `ApiConstants`
- **Enum Separation**: Business enums (`Role`, `OrderStatus`) in dedicated `model.enums` package

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Runtime | Java 17 |
| Framework | Spring Boot 3.2.5 |
| Web | Spring Web (RESTful API) |
| Security | Spring Security 6 + JWT (jjwt 0.11.5) |
| Data | Spring Data JPA + Hibernate |
| Validation | Jakarta Bean Validation (JSR-303) |
| Database | MySQL 8.0 |
| API Docs | SpringDoc OpenAPI 2.5.0 (Swagger UI) |
| Build | Maven + Maven Wrapper |
| Deploy | Docker + Docker Compose |

---

## 📂 Project Structure

```
src/main/java/com/ecommerce/api/
│
├── config/                 # Application configuration
│   ├── SecurityConfig      # Spring Security + CORS + JWT filter chain
│   ├── OpenApiConfig       # Swagger/OpenAPI with JWT bearer scheme
│   └── DataInitializer     # Auto-seeds admin, user, categories, products
│
├── constant/               # Centralized API response messages
│   └── ApiConstants
│
├── controller/             # REST API endpoints (5 controllers)
│   ├── AuthController      # POST /api/v1/auth/login, /register
│   ├── ProductController   # CRUD + /search with filters
│   ├── CategoryController  # CRUD
│   ├── CartController      # GET cart, POST add item, DELETE remove item
│   └── OrderController     # POST checkout, GET history, PUT status
│
├── dto/
│   ├── request/            # Input DTOs with Bean Validation
│   └── response/           # Output DTOs (ApiResponse, ProductResponse, ...)
│
├── entity/                 # JPA Entities with manual Builder pattern
│   ├── BaseEntity          # Abstract base: id, createdAt, updatedAt, deleted
│   ├── User, Product, Category, Cart, CartItem, Order, OrderItem
│
├── exception/              # Global error handling
│   ├── GlobalExceptionHandler     # @RestControllerAdvice
│   ├── ResourceNotFoundException  # 404
│   └── EcommerceApiException      # Custom business exceptions
│
├── mapper/                 # Entity ↔ DTO conversion (4 mappers)
├── model/enums/            # Role, OrderStatus
├── repository/             # Spring Data JPA repositories
├── security/               # JWT infrastructure
│   ├── JwtTokenProvider           # Token generation & validation
│   ├── JwtAuthenticationFilter    # OncePerRequestFilter
│   ├── JwtAuthenticationEntryPoint # 401 handler
│   └── CustomUserDetailsService   # Loads user by email
│
├── service/                # Business logic interfaces
├── service/impl/           # Business logic implementations
└── specification/          # JPA Criteria API for dynamic queries
    └── ProductSpecification
```

---

## 🔗 API Endpoints

### Auth
| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/api/v1/auth/register` | Public | Register a new user |
| `POST` | `/api/v1/auth/login` | Public | Login and receive JWT token |

### Products
| Method | Endpoint | Access | Description |
|---|---|---|---|
| `GET` | `/api/v1/products` | Public | List all products (paginated) |
| `GET` | `/api/v1/products/{id}` | Public | Get product by ID |
| `GET` | `/api/v1/products/search` | Public | Search with filters (name, category, price range) |
| `POST` | `/api/v1/products` | Admin | Create product |
| `PUT` | `/api/v1/products/{id}` | Admin | Update product |
| `DELETE` | `/api/v1/products/{id}` | Admin | Soft-delete product |

### Categories
| Method | Endpoint | Access | Description |
|---|---|---|---|
| `GET` | `/api/v1/categories` | Public | List all categories |
| `POST` | `/api/v1/categories` | Admin | Create category |
| `PUT` | `/api/v1/categories/{id}` | Admin | Update category |
| `DELETE` | `/api/v1/categories/{id}` | Admin | Soft-delete category |

### Cart
| Method | Endpoint | Access | Description |
|---|---|---|---|
| `GET` | `/api/v1/cart` | User | Get current user's cart |
| `POST` | `/api/v1/cart/items` | User | Add item to cart |
| `DELETE` | `/api/v1/cart/items/{productId}` | User | Remove item from cart |

### Orders
| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/api/v1/orders/checkout` | User | Checkout cart → create order |
| `GET` | `/api/v1/orders/history` | User | Get order history |
| `PUT` | `/api/v1/orders/{id}/status` | Admin | Update order status |

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.9+
- MySQL 8.0 (or Docker)

### Option 1 — Docker Compose (Recommended)

```bash
docker-compose up --build -d
```

This spins up both MySQL and the Spring Boot app. The API will be live at `http://localhost:8080`.

### Option 2 — Run Locally

1. Start MySQL and create a database:
   ```sql
   CREATE DATABASE ecommerce_db;
   ```

2. Update `src/main/resources/application.yml` if your MySQL credentials differ from the defaults (`root` / `password`).

3. Build and run:
   ```bash
   ./mvnw spring-boot:run
   ```

---

## 📖 API Documentation (Swagger)

Once the app is running, open:

| Resource | URL |
|---|---|
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| OpenAPI JSON | http://localhost:8080/v3/api-docs |

**How to authenticate in Swagger UI:**
1. Call `POST /api/v1/auth/login` with credentials below
2. Copy the `accessToken` from the response
3. Click **Authorize** 🔓 → paste: `Bearer <your-token>` → Confirm

---

## 🔑 Default Test Accounts

The application auto-seeds these accounts on first startup:

| Role | Email | Password |
|---|---|---|
| Admin | `admin@example.com` | `admin123` |
| User | `user@example.com` | `user123` |

---

## 🧪 Build & Verify

```bash
# Compile
./mvnw clean compile

# Run tests
./mvnw test

# Package as JAR
./mvnw clean package -DskipTests

# Run the JAR
java -jar target/api-0.0.1-SNAPSHOT.jar
```

---
