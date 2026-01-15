# Shop App Backend - Improved Development Task List

This checklist prioritizes core business features using simple Basic Authentication first, delaying complex JWT implementation until the end. Each phase includes verification steps and testing requirements.

---

## Phase 1: Foundation & "Dev-Mode" Security

**Goal**: Get the app running with basic infrastructure and temporary security.

### 1.1 Project Initialization

- [x] Setup Spring Boot project with dependencies:
  - [x] spring-boot-starter-web
  - [x] spring-boot-starter-data-jpa
  - [x] mysql-connector-j
  - [x] spring-boot-starter-validation
  - [x] lombok
  - [x] mapstruct (1.5.5.Final)
- [x] Configure `application.yml`:
  - [x] Database connection (MySQL URL, username, password)
  - [x] Server port (8080)
  - [x] JPA settings (ddl-auto: validate, show-sql: true)
- [x] Setup **Flyway** for database migrations:
  - [x] Add Flyway dependencies
  - [x] Create `V1__init_schema.sql` (users, roles, categories tables)
  - [x] Configure Flyway in application.yml
  - [x] **Verify**: Run app, check Flyway migrations executed - dùng docker-nếu đã import db vào thì tạm tắt flyway

### 1.2 Error Handling & Validation Infrastructure (làm sau cùng)

- [ ] Create `@ControllerAdvice` for global exception handling:
  - [ ] Create `GlobalExceptionHandler` class
  - [ ] Handle `MethodArgumentNotValidException`
  - [ ] Handle `ResourceNotFoundException`
  - [ ] Define standard error response DTO
- [ ] Configure Bean Validation:
  - [ ] Create `ValidationMessages.properties` for i18n
  - [ ] Setup `LocalValidatorFactoryBean`
- [ ] Configure Logging:
  - [ ] Setup `logback-spring.xml` for dev/prod profiles
  - [ ] Set Spring Security logging to DEBUG for development
  - [ ] **Verify**: Trigger validation error, check log output

### 1.3 Basic Security Setup (Temporary) (làm sau)

- [ ] Add `spring-boot-starter-security` dependency
- [ ] Implement `Spring Security` with **HTTP Basic Auth**:
  - [ ] Create `SecurityConfig` class with `@Configuration`
  - [ ] Define `SecurityFilterChain` bean
  - [ ] Configure Basic Auth with `httpBasic()`
- [ ] Create 2 test users:
  - [ ] Option A: Hardcoded `UserDetailsService` with in-memory users
  - [ ] Option B: Load from database (preferred if User entity ready)
  - [ ] User 1: username=`admin`, password=`admin123`, role=`ADMIN`
  - [ ] User 2: username=`user`, password=`user123`, role=`USER`
- [ ] Configure endpoint protection:
  - [ ] `/api/v1/admin/**` requires `ADMIN` role
  - [ ] `/api/v1/users/**` requires authentication
  - [ ] Public endpoints: `/api/v1/categories`, `/api/v1/products` (GET only)
- [ ] **Verify**:
  - [ ] Login via Postman with Basic Auth credentials
  - [ ] Access admin endpoint with user credentials (should fail)
  - [ ] Access public endpoint without auth (should succeed)

---

## Phase 2: Core Data Management

**Goal**: Implement fundamental entities and CRUD operations.

### 2.1 Category Management

- [x] Create Entity `Category`:
  - [x] Fields: `id`, `name`
  - [x] Add `@Entity`, `@Table(name = "categories")`
  - [x] Add validation annotations (`@NotBlank` on name)
- [x] Create `CategoryRepository extends JpaRepository`
- [x] Create `CategoryDTO` and mapper (MapStruct or ModelMapper)
- [x] Implement `CategoryService`:
  - [x] `getAllCategories()`
  - [x] `getCategoryById(Long id)`
  - [x] `createCategory(CategoryDTO dto)`
  - [x] `updateCategory(Long id, CategoryDTO dto)`
  - [x] `deleteCategory(Long id)`
- [ ] Create `CategoryController`:
  - [ ] `GET /api/v1/categories` - Public
  - [ ] `POST /api/v1/categories` - ADMIN only
  - [ ] `PUT /api/v1/categories/{id}` - ADMIN only
  - [ ] `DELETE /api/v1/categories/{id}` - ADMIN only
- [ ] **Testing**:
  - [ ] Write integration tests for all endpoints
  - [ ] Test authorization (admin vs user vs public)
- [ ] **Verify**:
  - [ ] CRUD operations work via Postman
  - [ ] Non-admin cannot create/update/delete

### 2.2 Product Management (Basic)

- [ ] Create Entity `Product`:
  - [ ] Fields: `id`, `name`, `price`, `thumbnail`, `description`
  - [ ] `@ManyToOne` relationship to `Category`
  - [ ] Extend `BaseEntity` (for `createdAt`, `updatedAt`)
- [ ] Create Flyway migration `V2__create_products_table.sql`
- [ ] Create `ProductRepository` with custom queries:
  - [ ] `findByNameContaining(String keyword, Pageable)`
  - [ ] `findByCategoryId(Long categoryId, Pageable)`
- [ ] Create `ProductDTO`, `ProductResponse`, mappers
- [ ] Implement `ProductService`:
  - [ ] `getAllProducts(PageRequest, keyword, categoryId)`
  - [ ] `getProductById(Long id)`
  - [ ] `createProduct(ProductDTO dto)`
  - [ ] `updateProduct(Long id, ProductDTO dto)`
  - [ ] `deleteProduct(Long id)`
- [ ] Create `ProductController`:
  - [ ] `GET /api/v1/products` - Public (with pagination & filtering)
  - [ ] `GET /api/v1/products/{id}` - Public
  - [ ] `POST /api/v1/products` - ADMIN only
  - [ ] `PUT /api/v1/products/{id}` - ADMIN only
  - [ ] `DELETE /api/v1/products/{id}` - ADMIN only
- [ ] Implement Pagination & Sorting:
  - [ ] Use `Pageable` parameter
  - [ ] Support sort by `price`, `createdAt`
- [ ] **Testing**:
  - [ ] Unit tests for ProductService
  - [ ] Integration tests for all endpoints
  - [ ] Test pagination and filtering
- [ ] **Verify**:
  - [ ] Admin can create products with valid category
  - [ ] Users can view paginated product list
  - [ ] Filtering by keyword and category works

### 2.3 User Management (Data Layer)

- [ ] Create Entity `Role`:
  - [ ] Fields: `id`, `name` (e.g., "ADMIN", "USER")
  - [ ] Flyway migration: `V3__create_roles_table.sql`
  - [ ] Seed initial roles in migration
- [ ] Create Entity `User`:
  - [ ] Fields: `id`, `fullName`, `phoneNumber`, `address`, `password`, `active`, `dateOfBirth`
  - [ ] `@ManyToOne` relationship to `Role`
  - [ ] Implement `UserDetails` interface
  - [ ] Extend `BaseEntity`
- [ ] Create `UserRepository`
- [ ] Create `UserDTO`, `RegisterDTO`, mappers
- [ ] Implement `UserService`:
  - [ ] `registerUser(RegisterDTO dto)` - Encode password with `BCryptPasswordEncoder`
  - [ ] `getUserById(Long id)`
  - [ ] `getUserByPhoneNumber(String phoneNumber)`
- [ ] Create `UserController`:
  - [ ] `POST /api/v1/users/register` - Public
  - [ ] `POST /api/v1/users/details` - Authenticated (returns user info from Basic Auth)
- [ ] **Testing**:
  - [ ] Test user registration with valid/invalid data
  - [ ] Test password encoding
- [ ] **Verify**:
  - [ ] New users can register successfully
  - [ ] Password is stored encrypted (BCrypt)
  - [ ] Registered user can login with Basic Auth
- [ ] **Note**: Login endpoint still uses Basic Auth. Token generation is placeholder (e.g., return empty string or "basic-auth-token").

---

## Phase 3: Business Logic & Interactions

**Goal**: Enable shop activities like ordering and product images.

### 3.1 Product Images

- [ ] Create Entity `ProductImage`:
  - [ ] Fields: `id`, `imageUrl`
  - [ ] `@ManyToOne` relationship to `Product`
- [ ] Create Flyway migration `V4__create_product_images_table.sql`
- [ ] Configure file storage:
  - [ ] Define upload directory in `application.yml`
  - [ ] Set max file size (`spring.servlet.multipart.max-file-size: 10MB`)
- [ ] Implement File Upload logic:
  - [ ] Create `FileStorageService`
  - [ ] Validate file type (only images: jpg, png, webp)
  - [ ] Sanitize filename (remove special characters)
  - [ ] Generate unique filename (UUID + original extension)
  - [ ] Save to disk and return URL
- [ ] Create `ProductImageController`:
  - [ ] `POST /api/v1/products/uploads/{id}` - ADMIN only
  - [ ] Accept `MultipartFile[]` (max 5 images)
  - [ ] Save each file and create `ProductImage` entries
- [ ] Implement Image Retrieval:
  - [ ] `GET /api/v1/products/images/{imageName}` - Public
  - [ ] Return image as `byte[]` with correct `MediaType`
- [ ] **Testing**:
  - [ ] Test valid image upload
  - [ ] Test invalid file type rejection
  - [ ] Test file size limit
- [ ] **Verify**:
  - [ ] Images uploaded successfully
  - [ ] Images retrievable via URL
  - [ ] Invalid files rejected

### 3.2 Order Processing

- [ ] Create Entity `Order`:
  - [ ] Fields: `id`, `userId`, `fullName`, `email`, `phoneNumber`, `address`, `note`, `orderDate`, `status`, `totalMoney`, `shippingMethod`, `shippingAddress`, `shippingDate`, `trackingNumber`, `paymentMethod`, `active`
  - [ ] `@ManyToOne` relationship to `User`
  - [ ] `@OneToMany` relationship to `OrderDetail`
- [ ] Create Entity `OrderDetail`:
  - [ ] Fields: `id`, `orderId`, `productId`, `price`, `numberOfProducts`, `totalMoney`, `color`
  - [ ] `@ManyToOne` relationship to `Order` and `Product`
- [ ] Create Flyway migrations `V5__create_orders_and_order_details.sql`
- [ ] Create `OrderDTO`, `OrderDetailDTO`, `OrderResponse`, mappers
- [ ] Implement `OrderService`:
  - [ ] `createOrder(OrderDTO dto)` - Validate products exist, calculate totals
  - [ ] `getOrdersByUserId(Long userId, Pageable)` - Filter by authenticated user
  - [ ] `getOrderById(Long orderId)` - Check user ownership or admin
  - [ ] `updateOrder(Long id, OrderDTO dto)` - ADMIN only
  - [ ] `deleteOrder(Long id)` - Soft delete (set `active = false`)
- [ ] Implement `OrderDetailService`:
  - [ ] `createOrderDetail(OrderDetailDTO dto)`
  - [ ] `getOrderDetailsByOrderId(Long orderId)`
  - [ ] `updateOrderDetail(Long id, OrderDetailDTO dto)`
  - [ ] `deleteOrderDetail(Long id)`
- [ ] Create `OrderController`:
  - [ ] `POST /api/v1/orders` - USER (extract user ID from Basic Auth)
  - [ ] `GET /api/v1/orders/user/{userId}` - USER (must match authenticated user) or ADMIN
  - [ ] `GET /api/v1/orders/{id}` - USER (must own order) or ADMIN
  - [ ] `GET /api/v1/orders/get-order-by-keyword` - ADMIN
  - [ ] `PUT /api/v1/orders/{id}` - ADMIN
  - [ ] `DELETE /api/v1/orders/{id}` - ADMIN
- [ ] Create `OrderDetailController`:
  - [ ] `POST /api/v1/order_details` - USER
  - [ ] `GET /api/v1/order_details/{id}` - USER or ADMIN
  - [ ] `GET /api/v1/order_details/order/{orderId}` - USER or ADMIN
  - [ ] `PUT /api/v1/order_details/{id}` - ADMIN
  - [ ] `DELETE /api/v1/order_details/{id}` - ADMIN
- [ ] **Testing**:
  - [ ] Test order creation with valid products
  - [ ] Test order retrieval (user sees only their orders)
  - [ ] Test admin can see all orders
- [ ] **Verify**:
  - [ ] User can place order
  - [ ] User can view their orders
  - [ ] Admin can manage all orders

### 3.3 Coupons & Calculation

- [ ] Create Entity `Coupon`:
  - [ ] Fields: `id`, `code`, `active`
- [ ] Create Entity `CouponCondition`:
  - [ ] Fields: `id`, `couponId`, `attribute`, `operator`, `value`, `discountAmount`
  - [ ] `@ManyToOne` relationship to `Coupon`
- [ ] Create Flyway migration `V6__create_coupons_and_conditions.sql`
- [ ] Implement `CouponService`:
  - [ ] `calculateCouponValue(String couponCode, double totalAmount)` - Evaluate conditions and return discount
- [ ] Create `CouponController`:
  - [ ] `GET /api/v1/coupons/calculate` - Public
  - [ ] Accept `couponCode` and `totalAmount` as query params
- [ ] **Testing**:
  - [ ] Test valid coupon with conditions met
  - [ ] Test invalid coupon code
  - [ ] Test conditions not met
- [ ] **Verify**:
  - [ ] Coupon calculation logic works correctly
  - [ ] Discount applied only when conditions satisfied

---

## Phase 4: Advanced Features & Optimization

**Goal**: Add polish with comments, caching, and search improvements.

### 4.1 Comments/Reviews

- [ ] Create Entity `Comment`:
  - [ ] Fields: `id`, `productId`, `userId`, `content`
  - [ ] `@ManyToOne` relationships to `Product` and `User`
  - [ ] Extend `BaseEntity`
- [ ] Create Flyway migration `V7__create_comments_table.sql`
- [ ] Create `CommentDTO`, `CommentResponse`, mappers
- [ ] Implement `CommentService`:
  - [ ] `getCommentsByProductId(Long productId)`
  - [ ] `createComment(CommentDTO dto)` - USER or ADMIN
  - [ ] `updateComment(Long id, CommentDTO dto)` - Owner or ADMIN
  - [ ] `deleteComment(Long id)` - Owner or ADMIN
- [ ] Create `CommentController`:
  - [ ] `GET /api/v1/comments` - Public (filter by productId)
  - [ ] `POST /api/v1/comments` - USER or ADMIN
  - [ ] `PUT /api/v1/comments/{id}` - USER (owner) or ADMIN
  - [ ] `DELETE /api/v1/comments/{id}` - USER (owner) or ADMIN
- [ ] **Testing**:
  - [ ] Test comment creation
  - [ ] Test user can only edit/delete their own comments
  - [ ] Test admin can edit/delete any comment
- [ ] **Verify**:
  - [ ] Users can post comments on products
  - [ ] Comments displayed correctly

### 4.2 Redis Caching

- [ ] Install Redis server:
  - [ ] Option A: Local installation
  - [ ] Option B: Docker container (`docker run -d -p 6379:6379 redis`)
- [ ] Add dependencies:
  - [ ] `spring-boot-starter-data-redis`
  - [ ] `lettuce-core`
- [ ] Configure Redis in `application.yml`:
  - [ ] Host, port (localhost:6379)
  - [ ] Connection pool settings
- [ ] Create `CacheConfig`:
  - [ ] Add `@EnableCaching`
  - [ ] Define `RedisCacheManager` bean
  - [ ] Configure cache key prefix and TTL
- [ ] Implement caching strategy:
  - [ ] Add `@Cacheable("products")` on `ProductService.getAllProducts()`
  - [ ] Add `@CacheEvict(value = "products", allEntries = true)` on `updateProduct()` and `deleteProduct()`
  - [ ] Add `@CachePut(value = "products", key = "#result.id")` on `createProduct()`
- [ ] **Testing**:
  - [ ] Check cache hit/miss in application logs
  - [ ] Verify cache invalidation on product updates
  - [ ] Use Redis CLI to inspect cached data (`redis-cli KEYS *`)
- [ ] **Load Testing**:
  - [ ] Use JMeter or Apache Bench to measure performance
  - [ ] Compare response times with/without cache
- [ ] **Verify**:
  - [ ] First request is slower (cache miss)
  - [ ] Subsequent requests are faster (cache hit)
  - [ ] Cache cleared on product modification

### 4.3 Advanced Search

- [ ] Enhance `ProductRepository`:
  - [ ] Add method with `@Query` for multi-criteria search
  - [ ] Support filtering by: name, category, price range, status
- [ ] Update `ProductService.getAllProducts()`:
  - [ ] Add parameters: `minPrice`, `maxPrice`, `status`
  - [ ] Build dynamic query or use Specification API
- [ ] Update `ProductController`:
  - [ ] Accept additional query parameters
- [ ] **Testing**:
  - [ ] Test search with multiple filters
  - [ ] Test search with no results
- [ ] **Verify**:
  - [ ] Search returns correct results for various criteria

### 4.4 Token Entity Preparation (for Phase 5)

- [ ] Create Entity `Token`:
  - [ ] Fields: `id`, `token`, `refreshToken`, `tokenType`, `expirationTime`, `refreshExpirationTime`, `revoked`, `expired`, `isMobile`
  - [ ] `@OneToOne` relationship to `User`
- [ ] Create Flyway migration `V8__create_tokens_table.sql`
- [ ] Create `TokenRepository`
- [ ] **Verify**: Migration runs successfully

---

## Phase 5: Production Security (The "Big Switch")

**Goal**: Replace Basic Auth with robust JWT Security.

### 5.1 JWT Implementation

- [ ] Verify `jjwt` dependencies in `pom.xml`:
  - [ ] `jjwt-api` (0.11.5)
  - [ ] `jjwt-impl` (0.11.5)
  - [ ] `jjwt-jackson` (0.11.5)
- [ ] Configure JWT settings in `application.yml`:
  - [ ] Secret key (base64 encoded, min 256 bits)
  - [ ] Access token expiration (15 minutes)
  - [ ] Refresh token expiration (7 days)
- [ ] Create `JwtTokenUtil`:
  - [ ] `generateToken(UserDetails userDetails)` - Returns access token
  - [ ] `generateRefreshToken(UserDetails userDetails)` - Returns refresh token
  - [ ] `validateToken(String token, UserDetails userDetails)` - Boolean
  - [ ] `extractUsername(String token)` - String
  - [ ] `extractExpiration(String token)` - Date
  - [ ] `isTokenExpired(String token)` - Boolean
- [ ] **Testing**:
  - [ ] Unit test token generation
  - [ ] Unit test token validation
  - [ ] Test expired token handling
- [ ] **Verify**:
  - [ ] Tokens generated successfully
  - [ ] Token validation logic works

### 5.2 JWT Filter

- [ ] Create `JwtTokenFilter extends OncePerRequestFilter`:
  - [ ] Extract token from `Authorization` header (format: `Bearer <token>`)
  - [ ] Validate token using `JwtTokenUtil`
  - [ ] Load `UserDetails` from `UserDetailsService`
  - [ ] Set `Authentication` in `SecurityContext`
  - [ ] Handle exceptions (invalid token, expired token)
- [ ] **Testing**:
  - [ ] Unit test filter logic
- [ ] **Verify**:
  - [ ] Filter extracts token correctly
  - [ ] Authentication set in context

### 5.3 Authentication Endpoints

- [ ] Implement `UserDetailsService`:
  - [ ] Override `loadUserByUsername(String phoneNumber)`
  - [ ] Load user from database
  - [ ] Return `User` entity (implements `UserDetails`)
- [ ] Update `UserService`:
  - [ ] `login(LoginDTO dto)` - Verify credentials, generate tokens
  - [ ] `refreshToken(String refreshToken)` - Validate refresh token, generate new access token
- [ ] Update `UserController`:
  - [ ] `POST /api/v1/users/login`:
    - [ ] Accept `LoginDTO` (phoneNumber, password)
    - [ ] Return `LoginResponse` (accessToken, refreshToken, userDetails)
  - [ ] `POST /api/v1/users/refresh-token`:
    - [ ] Accept `RefreshTokenDTO` (refreshToken)
    - [ ] Return new `accessToken`
- [ ] Implement Token persistence:
  - [ ] Save tokens to `Token` entity on login
  - [ ] Revoke old tokens on new login
  - [ ] Mark tokens as expired/revoked on logout
- [ ] **Testing**:
  - [ ] Test login with valid credentials
  - [ ] Test login with invalid credentials
  - [ ] Test refresh token flow
  - [ ] Test token revocation
- [ ] **Verify**:
  - [ ] Login returns valid JWT tokens
  - [ ] Refresh token generates new access token

### 5.4 Parallel Auth Support (Transition Phase)

- [ ] Update `SecurityConfig` to support BOTH Basic Auth AND JWT:
  - [ ] Add JWT filter to filter chain (before `UsernamePasswordAuthenticationFilter`)
  - [ ] Keep Basic Auth configuration active
  - [ ] Add feature flag in `application.yml`: `security.jwt.enabled: true`
- [ ] Update controllers to accept both auth methods:
  - [ ] Extract user from `Authentication` object (works for both)
- [ ] **Testing**:
  - [ ] Test endpoints with Basic Auth (should still work)
  - [ ] Test endpoints with JWT (should work)
- [ ] **Verify**:
  - [ ] Both authentication methods work simultaneously
  - [ ] No conflicts between auth mechanisms

### 5.5 Final Switch to JWT Only

- [ ] Disable Basic Auth in `SecurityConfig`:
  - [ ] Remove `httpBasic()` configuration
  - [ ] Remove hardcoded test users
- [ ] Remove feature flag from `application.yml`
- [ ] Update all integration tests:
  - [ ] Replace Basic Auth with JWT in test setup
  - [ ] Create helper method to generate test tokens
- [ ] Update API documentation (Swagger):
  - [ ] Configure `SecurityScheme` for Bearer token
  - [ ] Add authorization button in Swagger UI
- [ ] **Testing**:
  - [ ] Run full test suite
  - [ ] Test all endpoints with JWT
  - [ ] Test unauthorized access (no token)
  - [ ] Test expired token handling
- [ ] **Verify**:
  - [ ] All endpoints work with JWT only
  - [ ] Basic Auth no longer accepted
  - [ ] Token expiration handled correctly
  - [ ] Refresh token flow works end-to-end

---

## Phase 6: Final Polish & Deployment Preparation

**Goal**: Prepare application for production deployment.

### 6.1 Security Hardening

- [ ] Implement logout endpoint:
  - [ ] `POST /api/v1/users/logout` - Revoke current token
- [ ] Add token blacklist mechanism (optional, for immediate revocation)
- [ ] Configure CORS properly for frontend domain
- [ ] Add rate limiting (e.g., using Bucket4j)
- [ ] **Verify**:
  - [ ] Logout revokes token
  - [ ] CORS allows frontend requests

### 6.2 Documentation

- [ ] Complete Swagger/OpenAPI documentation:
  - [ ] Add descriptions to all endpoints
  - [ ] Document request/response schemas
  - [ ] Add example values
- [ ] Create Postman collection with example requests
- [ ] Update README with:
  - [ ] Setup instructions
  - [ ] API documentation link
  - [ ] Authentication flow diagram

### 6.3 Monitoring & Health Checks

- [ ] Configure Spring Boot Actuator:
  - [ ] Enable health, info, metrics endpoints
  - [ ] Secure actuator endpoints (ADMIN only)
- [ ] Create custom health indicators (e.g., database, Redis)
- [ ] **Verify**:
  - [ ] `/actuator/health` returns system status
  - [ ] Metrics available for monitoring

### 6.4 Production Configuration

- [ ] Create production profile (`application-prod.yml`):
  - [ ] Disable DEBUG logging
  - [ ] Disable Swagger UI (optional)
  - [ ] Configure external database
  - [ ] Set secure JWT secret key
- [ ] Setup environment variables for sensitive data
- [ ] Create Docker Compose for production stack
- [ ] **Verify**:
  - [ ] Application runs in production mode
  - [ ] No sensitive data in logs

---

## Completion Checklist

- [ ] All endpoints tested and working
- [ ] JWT authentication fully functional
- [ ] Database migrations applied
- [ ] Redis caching operational
- [ ] Error handling comprehensive
- [ ] API documentation complete
- [ ] Production configuration ready
- [ ] Security best practices implemented

**Estimated Timeline**: 8-12 weeks (depending on experience level)

**Notes**:

- Always commit code after each completed task
- Write tests as you go, not at the end
- Use feature branches for each phase
- Review security configuration regularly
