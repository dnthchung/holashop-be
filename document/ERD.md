# Entity Relationship Diagram (ERD) - hola_shop Backend

## Overview

This document outlines the database schema and entity relationships for the hola_shop Backend system.

## Mermaid Diagram

```mermaid
erDiagram
    Role ||--o{ User : "assigned_to"
    User ||--o{ Token : "owns"
    User ||--o{ SocialAccount : "has"
    User ||--o{ Order : "places"
    User ||--o{ Comment : "writes"
    
    Category ||--o{ Product : "contains"
    Product ||--o{ ProductImage : "has"
    Product ||--o{ OrderDetail : "appears_in"
    Product ||--o{ Comment : "has"
    
    Order ||--o{ OrderDetail : "composed_of"
    Order }|--|| Coupon : "applies"
    OrderDetail }|--|| Coupon : "applies"
    
    Coupon ||--o{ CouponCondition : "has_conditions"

    Role {
        Long id PK
        String name
    }

    User {
        Long id PK
        String fullname
        String phone_number
        String address
        String password
        Boolean is_active
        Date date_of_birth
        int facebook_account_id
        int google_account_id
        Long role_id FK
    }

    Token {
        Long id PK
        String token
        String refresh_token
        String token_type
        Instant expiration_time
        Instant refresh_expiration_time
        Boolean revoked
        Boolean expired
        Boolean is_mobile
        Long user_id FK
    }

    SocialAccount {
        Long id PK
        String provider
        String provider_id
        String email
        String name
        Long user_id FK
    }

    Category {
        Long id PK
        String name
    }

    Product {
        Long id PK
        String name
        Float price
        String thumbnail
        String description
        Long category_id FK
        DateTime created_at
        DateTime updated_at
    }

    ProductImage {
        Long id PK
        String image_url
        Long product_id FK
    }

    Order {
        Long id PK
        String fullname
        String email
        String phone_number
        String address
        String note
        DateTime order_date
        String status
        Float total_money
        String shipping_method
        String shipping_address
        LocalDate shipping_date
        String tracking_number
        String payment_method
        Boolean active
        Long user_id FK
        Long coupon_id FK
    }

    OrderDetail {
        Long id PK
        Float price
        Integer number_of_products
        Float total_money
        String color
        Long order_id FK
        Long product_id FK
        Long coupon_id FK
    }

    Comment {
        Long id PK
        String content
        Long product_id FK
        Long user_id FK
        DateTime created_at
        DateTime updated_at
    }

    Coupon {
        Long id PK
        String code
        Boolean active
    }

    CouponCondition {
        Long id PK
        String attribute
        String operator
        String value
        BigDecimal discount_amount
        Long coupon_id FK
    }
```

## Entities Description

### 1. User Management
*   **User**: Stores user account information including credentials, profile details, and role association.
*   **Role**: Defines user roles (e.g., ADMIN, USER) for permission control.
*   **Token**: Manages JWT authentication tokens and refresh tokens, tracking validity and device type (mobile/web).
*   **SocialAccount**: Links users to external social login providers (Facebook, Google).

### 2. Product Management
*   **Category**: Groups products into logical categories (e.g., Electronics, Furniture).
*   **Product**: Contains core product details such as name, price, description, and thumbnail.
*   **ProductImage**: Stores URLs for additional images associated with a product.
*   **Comment**: Allows users to leave feedback/reviews on products.

### 3. Order Management
*   **Order**: Represents a purchase transaction, tracking status, shipping details, total amount, and the user who placed it.
*   **OrderDetail**: Links specific products to an order, capturing the quantity, price at the time of purchase, and selected attributes (e.g., color).

### 4. Marketing & Promotions
*   **Coupon**: Defines discount codes available for use.
*   **CouponCondition**: Specifies rules for applying coupons (e.g., minimum order amount, applicable date range) and the discount value.

## Database Schema History
*   The project uses **Flyway** for database migrations, ensuring schema consistency across environments. Key migrations include:
    *   `V1`: Initial schema adjustments (Category name unique, Product price decimal, etc.).
    *   `V2`: Added `is_mobile` to Tokens.
    *   `V3`: Added `refresh_expiration_time` to Tokens.
    *   `V4`: Created `comments` table.
    *   `V5`: Created `coupons` and `coupon_conditions` tables and linked them to orders.
