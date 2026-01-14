# Shopp App Backend - API List

The base URL for all APIs is: `${api.prefix}` (e.g., `/api/v1`)

## 1. Categories
| Method | Endpoint | Description | Role Required |
| :--- | :--- | :--- | :--- |
| `POST` | `/categories` | Create a new category | `ADMIN` |
| `GET` | `/categories` | Get all categories | Public |
| `PUT` | `/categories/{id}` | Update a category | `ADMIN` |
| `DELETE` | `/categories/{id}` | Delete a category | `ADMIN` |

## 2. Comments
| Method | Endpoint | Description | Role Required |
| :--- | :--- | :--- | :--- |
| `GET` | `/comments` | Get all comments by product ID (optional user ID) | Public |
| `PUT` | `/comments/{id}` | Update a comment | `ADMIN`, `USER` |
| `POST` | `/comments` | Insert a new comment | `ADMIN`, `USER` |
| `DELETE` | `/comments/{id}` | Delete a comment | `ADMIN`, `USER` |

## 3. Coupons
| Method | Endpoint | Description | Role Required |
| :--- | :--- | :--- | :--- |
| `GET` | `/coupons/calculate` | Calculate coupon value | Public |

## 4. Health Check
| Method | Endpoint | Description | Role Required |
| :--- | :--- | :--- | :--- |
| `GET` | `/health_check/health` | Check application health | Public |

## 5. Orders
| Method | Endpoint | Description | Role Required |
| :--- | :--- | :--- | :--- |
| `POST` | `/orders` | Create a new order | `USER` |
| `GET` | `/orders/user/{user_id}` | Get orders by user ID | `ADMIN`, `USER` |
| `GET` | `/orders/{id}` | Get order details by order ID | `ADMIN`, `USER` |
| `GET` | `/orders/get-order-by-keyword` | Get orders by keyword (admin search) | `ADMIN` |
| `PUT` | `/orders/{id}` | Update an order | `ADMIN` |
| `DELETE` | `/orders/{id}` | Delete an order (soft delete) | `ADMIN` |

## 6. Order Details
| Method | Endpoint | Description | Role Required |
| :--- | :--- | :--- | :--- |
| `POST` | `/order_details` | Create order detail | `USER` |
| `GET` | `/order_details/{id}` | Get order detail by ID | `ADMIN`, `USER` |
| `GET` | `/order_details/order/{orderId}` | Get order details by order ID | `ADMIN`, `USER` |
| `PUT` | `/order_details/{id}` | Update order detail | `ADMIN` |
| `DELETE` | `/order_details/{id}` | Delete order detail | `ADMIN` |

## 7. Products
| Method | Endpoint | Description | Role Required |
| :--- | :--- | :--- | :--- |
| `POST` | `/products` | Create a new product | `ADMIN` |
| `POST` | `/products/uploads/{id}` | Upload images for a product | `ADMIN` |
| `GET` | `/products/images/{image-name}` | View product image | Public |
| `GET` | `/products` | Get products with filtering and pagination | Public |
| `GET` | `/products/{id}` | Get product by ID | Public |
| `GET` | `/products/details` | Get product details by ID | Public |
| `GET` | `/products/by-ids` | Get products by a list of IDs | Public |
| `PUT` | `/products/{id}` | Update a product | `ADMIN` |
| `DELETE` | `/products/{id}` | Delete a product | `ADMIN` |
| `POST` | `/products/generate-faceker-products` | Generate fake product data | `ADMIN` |

## 8. Roles
| Method | Endpoint | Description | Role Required |
| :--- | :--- | :--- | :--- |
| `GET` | `/roles` | Get all roles | Public |

## 9. Users
| Method | Endpoint | Description | Role Required |
| :--- | :--- | :--- | :--- |
| `POST` | `/users/register` | Register a new user | Public |
| `POST` | `/users/login` | User login | Public |
| `POST` | `/users/refresh-token` | Refresh authentication token | Public |
| `POST` | `/users/details` | Get user details (via token) | Public (Authenticated) |
| `PUT` | `/users/details/{userId}` | Update user details | `USER` |
| `GET` | `/users` | Get all users with pagination | `ADMIN` |
| `PUT` | `/users/reset-password/{userId}` | Reset user password | `ADMIN` |
| `PUT` | `/users/block/{userId}/{active}` | Block or unblock a user | `ADMIN` |
