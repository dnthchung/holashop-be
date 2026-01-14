# Shop App Backend System Features

## 1. User Management
- **Registration**: Allows new users to create an account (`POST /users/register`).
- **Authentication (Login)**: Users can log in using their phone number and password to receive a JWT token (`POST /users/login`).
- **Refresh Token**: Ability to refresh access tokens to maintain session validity (`POST /users/refresh-token`).
- **User Details**: 
  - Get current user details via token (`POST /users/details`).
  - Update user information (`PUT /users/details/{userId}`).
- **Admin Features**:
  - Get list of all users with pagination (`GET /users`).
  - Reset user password (`PUT /users/reset-password/{userId}`).
  - Block/Enable user accounts (`PUT /users/block/{userId}/{active}`).

## 2. Category Management
- **View Categories**: Public access to list all product categories (`GET /categories`).
- **Admin Features**:
  - Create new categories (`POST /categories`).
  - Update existing categories (`PUT /categories/{id}`).
  - Delete categories (`DELETE /categories/{id}`).

## 3. Product Management
- **View Products**: 
  - List products with pagination, sorting, and filtering by keyword/category (`GET /products`).
  - View product details (`GET /products/{id}`, `GET /products/details`).
  - Get multiple products by ID list (`GET /products/by-ids`).
  - View product images (`GET /products/images/{image-name}`).
- **Admin Features**:
  - Create new products (`POST /products`).
  - Upload product images (`POST /products/uploads/{id}`).
  - Update product information (`PUT /products/{id}`).
  - Delete products (`DELETE /products/{id}`).
  - Generate fake product data (`POST /products/generate-faceker-products`).
- **Caching**: Uses Redis to cache product lists for improved performance.

## 4. Order Management
- **Place Order**: Users can create new orders (`POST /orders`).
- **View Orders**:
  - Get all orders for a specific user (`GET /orders/user/{user_id}`).
  - Get specific order details (`GET /orders/{id}`).
- **Admin Features**:
  - Search/List all orders with pagination (`GET /orders/get-order-by-keyword`).
  - Update order status/details (`PUT /orders/{id}`).
  - Soft delete orders (`DELETE /orders/{id}`).

## 5. Order Detail Management
- **Create Details**: Add specific line items to an order (`POST /order_details`).
- **View Details**:
  - Get a specific order detail (`GET /order_details/{id}`).
  - Get all details for a specific order (`GET /order_details/order/{orderId}`).
- **Admin Features**:
  - Update order details (`PUT /order_details/{id}`).
  - Delete order details (`DELETE /order_details/{id}`).

## 6. Comment System
- **View Comments**: Retrieve comments for a product or by a user (`GET /comments`).
- **Manage Comments**:
  - Users can post comments (`POST /comments`).
  - Users/Admins can update their comments (`PUT /comments/{id}`).
  - Users/Admins can delete comments (`DELETE /comments/{id}`).

## 7. Coupons & Promotions
- **Calculate Value**: Calculate the discount amount for a given coupon code and total order value (`GET /coupons/calculate`).

## 8. Role Management
- **View Roles**: Public access to list available system roles (`GET /roles`).

## 9. System & Infrastructure
- **Security**: 
  - Stateless authentication using JWT (JSON Web Tokens).
  - Role-based access control (RBAC) ensuring only authorized users can access specific endpoints (e.g., Admin vs User).
- **Database**:
  - MySQL for persistent storage.
  - **Flyway** for database migration and version control.
- **Caching**: **Redis** integration for caching frequently accessed data (like Products).
- **API Documentation**: Integrated **Swagger/OpenAPI** for API exploration and testing.
- **Health Check**: Endpoint to verify system status and host information (`GET /health_check/health`).
- **Localization**: Support for message translation (i18n).
