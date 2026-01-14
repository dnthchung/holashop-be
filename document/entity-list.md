# Shopp App Backend - Entity List

This document lists all the JPA entities defined in the `shopapp-backend` project.

## 1. BaseEntity
*   **Description:** A mapped superclass that provides common auditing fields for other entities.
*   **Fields:**
    *   `createdAt` (LocalDateTime): The timestamp when the entity was created.
    *   `updatedAt` (LocalDateTime): The timestamp when the entity was last updated.

## 2. Category
*   **Table:** `categories`
*   **Description:** Represents a product category.
*   **Fields:**
    *   `id` (Long): Primary key.
    *   `name` (String): The name of the category.

## 3. Comment
*   **Table:** `comments`
*   **Description:** Represents a user comment on a product. Extends `BaseEntity`.
*   **Fields:**
    *   `id` (Long): Primary key.
    *   `product` (Product): The product being commented on (Many-to-One).
    *   `user` (User): The user who made the comment (Many-to-One).
    *   `content` (String): The content of the comment.

## 4. Coupon
*   **Table:** `coupons`
*   **Description:** Represents a discount coupon.
*   **Fields:**
    *   `id` (Long): Primary key.
    *   `code` (String): The unique coupon code.
    *   `active` (boolean): Whether the coupon is currently active.

## 5. CouponCondition
*   **Table:** `coupon_conditions`
*   **Description:** Represents conditions associated with a coupon.
*   **Fields:**
    *   `id` (Long): Primary key.
    *   `coupon` (Coupon): The associated coupon (Many-to-One).
    *   `attribute` (String): The attribute to check (e.g., "minimum_amount").
    *   `operator` (String): The operator for comparison (e.g., ">").
    *   `value` (String): The value to compare against.
    *   `discountAmount` (BigDecimal): The discount amount provided.

## 6. Order
*   **Table:** `orders`
*   **Description:** Represents a customer order.
*   **Fields:**
    *   `id` (Long): Primary key.
    *   `user` (User): The user who placed the order (Many-to-One).
    *   `fullName` (String): Full name of the recipient.
    *   `email` (String): Email of the recipient.
    *   `phoneNumber` (String): Phone number of the recipient.
    *   `address` (String): Shipping address.
    *   `note` (String): Order notes.
    *   `orderDate` (Date): Date the order was placed.
    *   `status` (String): Current status of the order.
    *   `totalMoney` (Float): Total monetary value of the order.
    *   `shippingMethod` (String): Shipping method used.
    *   `shippingAddress` (String): Detailed shipping address.
    *   `shippingDate` (LocalDate): Date of shipping.
    *   `trackingNumber` (String): Tracking number for the shipment.
    *   `paymentMethod` (String): Payment method used.
    *   `active` (boolean): Soft delete flag.
    *   `orderDetails` (List<OrderDetail>): List of items in the order (One-to-Many).

## 7. OrderDetail
*   **Table:** `order_details`
*   **Description:** Represents a specific item within an order.
*   **Fields:**
    *   `id` (Long): Primary key.
    *   `order` (Order): The associated order (Many-to-One).
    *   `product` (Product): The product ordered (Many-to-One).
    *   `price` (Float): Price of the product at the time of order.
    *   `numberOfProducts` (Integer): Quantity of the product.
    *   `totalMoney` (Float): Total cost for this line item.
    *   `color` (String): Color of the product (optional).

## 8. Product
*   **Table:** `products`
*   **Description:** Represents a product available in the shop. Extends `BaseEntity`.
*   **Fields:**
    *   `id` (Long): Primary key.
    *   `name` (String): Name of the product.
    *   `price` (Float): Price of the product.
    *   `thumbnail` (String): URL/path to the product's thumbnail image.
    *   `description` (String): Detailed description of the product.
    *   `category` (Category): The category the product belongs to (Many-to-One).
    *   `productImages` (List<ProductImage>): List of additional images for the product (One-to-Many).
    *   `comments` (List<Comment>): List of comments on the product (One-to-Many).

## 9. ProductImage
*   **Table:** `product_images`
*   **Description:** Represents an image associated with a product.
*   **Fields:**
    *   `id` (Long): Primary key.
    *   `product` (Product): The associated product (Many-to-One).
    *   `imageUrl` (String): URL/path to the image.

## 10. Role
*   **Table:** `roles`
*   **Description:** Represents a user role (e.g., ADMIN, USER).
*   **Fields:**
    *   `id` (Long): Primary key.
    *   `name` (String): Name of the role.

## 11. SocialAccount
*   **Table:** `social_accounts`
*   **Description:** Represents a social media account linked to a user.
*   **Fields:**
    *   `id` (Long): Primary key.
    *   `provider` (String): The social login provider (e.g., Google, Facebook).
    *   `providerId` (String): The user's ID from the provider.
    *   `email` (String): Email associated with the social account.
    *   `name` (String): User's name from the social account.
    *   `user` (User): The linked system user (Many-to-One).

## 12. Token
*   **Table:** `tokens`
*   **Description:** Represents an authentication token (JWT).
*   **Fields:**
    *   `id` (Long): Primary key.
    *   `token` (String): The token string.
    *   `refreshToken` (String): The refresh token string.
    *   `tokenType` (String): The type of token (e.g., Bearer).
    *   `expirationTime` (Instant): Expiration time of the token.
    *   `refreshExpirationTime` (Instant): Expiration time of the refresh token.
    *   `revoked` (boolean): Whether the token has been revoked.
    *   `expired` (boolean): Whether the token has expired.
    *   `isMobile` (boolean): Whether the token was issued to a mobile device.
    *   `user` (User): The user associated with the token (One-to-One).

## 13. User
*   **Table:** `users`
*   **Description:** Represents a system user. Extends `BaseEntity` and implements `UserDetails`.
*   **Fields:**
    *   `id` (Long): Primary key.
    *   `fullName` (String): Full name of the user.
    *   `phoneNumber` (String): Phone number (used as username).
    *   `address` (String): User's address.
    *   `password` (String): Encrypted password.
    *   `active` (boolean): Whether the account is active.
    *   `dateOfBirth` (Date): User's date of birth.
    *   `facebookAccountId` (int): ID for linked Facebook account.
    *   `googleAccountId` (int): ID for linked Google account.
    *   `role` (Role): The user's role (Many-to-One).
    *   `comments` (List<Comment>): List of comments made by the user (One-to-Many).
