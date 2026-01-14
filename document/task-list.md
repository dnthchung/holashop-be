# Shop App Backend - Development Task List

This checklist allows for the development of core business features using simple Basic Authentication first, delaying complex JWT implementation until the end.

## Phase 1: Foundation & "Dev-Mode" Security
*Goal: Get the app running and able to distinguish Admin vs User without complex tokens.*

- [ ] **Project Initialization**
    - [ ] Setup Spring Boot project with dependencies (Web, Data JPA, MySQL, Validation, Lombok).
    - [ ] Configure `application.yml` (Database URL, Server Port).
    - [ ] Setup **Flyway** for database migrations (Initial schema: users, roles).
- [ ] **Basic Security Setup (Temporary)**
    - [ ] Implement `Spring Security` with **HTTP Basic Auth**.
    - [ ] Create 2 hardcoded users (or simple DB lookup) for testing:
        - `admin` (Role: ADMIN)
        - `user` (Role: USER)
    - [ ] Configure `SecurityFilterChain` to protect endpoints (e.g., `/api/v1/admin/**` requires ADMIN).
    - [ ] **Verify**: Login via Postman using Basic Auth credentials.

## Phase 2: Core Data Management
*Goal: Manage the fundamental entities of the shop.*

- [ ] **Category Management**
    - [ ] Create Entity `Category` & Repository.
    - [ ] Implement Service & Controller:
        - `GET /categories`: Public.
        - `POST/PUT/DELETE /categories`: ADMIN only.
- [ ] **Product Management (Basic)**
    - [ ] Create Entity `Product` (with Foreign Key to Category).
    - [ ] Implement CRUD operations.
    - [ ] Implement Pagination & Sorting.
    - [ ] **Verify**: Admin can create products; Users can view list.
- [ ] **User Management (Data Layer)**
    - [ ] Create Entity `User` & `Role`.
    - [ ] Implement Registration (`POST /users/register`): Save user to DB (password encoded).
    - [ ] *Note: Login endpoint will still use Basic Auth for now, or returns a dummy token if frontend needs it.*

## Phase 3: Business Logic & Interactions
*Goal: Allow users to perform shop activities.*

- [ ] **Product Images**
    - [ ] Create Entity `ProductImage`.
    - [ ] Implement File Upload logic (`POST /products/uploads/{id}`).
    - [ ] Implement Image Retrieval (`GET /products/images/{name}`).
- [ ] **Order Processing**
    - [ ] Create Entities `Order` and `OrderDetail`.
    - [ ] Implement "Place Order" (`POST /orders`).
    - [ ] Implement "Get My Orders" (Filter by logged-in User ID from Basic Auth).
    - [ ] Admin: "Manage Orders" (Update status, Delete).
- [ ] **Coupons & Calculation**
    - [ ] Create Entity `Coupon`.
    - [ ] Implement Logic to calculate discount.

## Phase 4: Advanced Features & Optimization
*Goal: Polish the application.*

- [ ] **Comments/Reviews**
    - [ ] Create Entity `Comment`.
    - [ ] Allow Users to comment on Products.
- [ ] **Redis Caching**
    - [ ] Setup Redis connection.
    - [ ] Cache `GET /products` requests.
    - [ ] Invalidate cache on Product Update/Delete.
- [ ] **Advanced Search**
    - [ ] Improve Product search (multiple criteria).

## Phase 5: Production Security (The "Big Switch")
*Goal: Replace Basic Auth with robust JWT Security.*

- [ ] **JWT Implementation**
    - [ ] Add `jjwt` dependencies.
    - [ ] Create `JwtTokenUtil` (Generate, Validate, Extract Claims).
    - [ ] Create `JwtTokenFilter` (Intercept requests, validate header).
- [ ] **Authentication Endpoints**
    - [ ] Implement Real `POST /users/login`: Verify password -> Return Access & Refresh Tokens.
    - [ ] Implement `POST /users/refresh-token`.
- [ ] **Switch Security Config**
    - [ ] Disable Basic Auth.
    - [ ] Enable `JwtTokenFilter` in `SecurityFilterChain`.
    - [ ] Protect all sensitive endpoints with JWT checks.
- [ ] **Final Verification**
    - [ ] Test entire flow with Token-based auth.
    - [ ] Verify Token expiration and Refresh flow.
    - [ ] Test Admin vs User access control with real Tokens.
