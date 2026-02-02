# 🧪 API Testing Documentation

> Hướng dẫn test API cho Holashop Backend sử dụng **Postman** và **Swagger**

---

## 📋 Tools Available

| Tool | Purpose | Access |
|------|---------|--------|
| **Postman** | Full API testing with collections | Import JSON files |
| **Swagger UI** | Interactive API documentation | http://localhost:8088/swagger-ui.html |

---

## 🟢 Postman Testing

### Files trong folder này:

```
document/api-test/
├── Holashop.postman_collection.json    ← Import vào Postman
├── Holashop.postman_environment.json   ← Environment variables  
├── POSTMAN_GUIDE.md                    ← Hướng dẫn chi tiết
└── README.md                           ← File này
```

### Quick Start

#### 1. Import vào Postman

```bash
1. Mở Postman
2. Click "Import" (top-left)
3. Drag & drop 2 files:
   ✅ Holashop.postman_collection.json
   ✅ Holashop.postman_environment.json
4. Click "Import"
```

#### 2. Chọn Environment

```bash
Dropdown góc phải trên → Chọn "Holashop Environment"
```

#### 3. Login để lấy token

```bash
1. Folder "1. Authentication & Users"
2. Click "1.2. Login ⭐"
3. Click "Send"
4. Token tự động được lưu vào environment! ✅
```

#### 4. Test các API

```bash
Tất cả requests đã có Bearer Token với {{access_token}}
Chỉ cần click Send!
```

### Features

- ✅ **Auto-save token** sau khi login
- ✅ **35+ API endpoints** organized trong folders
- ✅ **Environment variables** cho flexible testing
- ✅ **Pre-request scripts** và **Tests scripts**
- ✅ **Bearer Token authentication** tự động

**Đọc thêm:** [POSTMAN_GUIDE.md](POSTMAN_GUIDE.md)

---

## 📖 Swagger UI

### Access Swagger

```
http://localhost:8088/swagger-ui.html
```

### Features

- ✅ Interactive API documentation
- ✅ Try-it-out functionality
- ✅ Request/Response schemas
- ✅ Auto-generated from code annotations

### Authentication với Swagger

1. Click **"Authorize"** button (góc phải trên)
2. Login bằng Postman trước để lấy token
3. Copy `access_token` từ Postman environment
4. Paste vào Swagger: `Bearer <your-token>`
5. Click **"Authorize"**
6. Giờ có thể test authenticated endpoints!

### Swagger Annotations

Code sử dụng **SpringDoc OpenAPI 3** annotations:

```java
@Operation(summary = "Get user details")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Success"),
    @ApiResponse(responseCode = "401", description = "Unauthorized")
})
```

---

## 🔐 Authentication Flow

### 1. Register (Optional)

```http
POST /api/v1/users/register
Content-Type: application/json

{
  "fullname": "Test User",
  "phone_number": "0123456789",
  "password": "password123",
  "retype_password": "password123",
  "address": "123 Test Street",
  "date_of_birth": "1990-01-01",
  "role_id": 1
}
```

### 2. Login ⭐ (MUST RUN FIRST!)

```http
POST /api/v1/users/login
Content-Type: application/json

{
  "phone_number": "0123456789",
  "password": "password123"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Login successfully",
  "payload": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "refresh_token": "dfcb5ada-8e45-48cb-..."
  }
}
```

**Postman tự động save:**
- `payload.token` → `{{access_token}}`
- `payload.refresh_token` → `{{refresh_token}}`

### 3. Use Token

**Postman:** Auto-use `{{access_token}}` in Bearer Token auth

**Swagger:** 
1. Copy token from Postman environment
2. Click "Authorize" in Swagger
3. Enter: `Bearer <token>`

### 4. Refresh Token (When Expired)

```http
POST /api/v1/users/refresh-token
Content-Type: application/json

{
  "refresh_token": "{{refresh_token}}"
}
```

---

## 📁 API Categories

### Collection Structure

```
Holashop API
├── 1. Authentication & Users (9 requests)
│   ├── Register
│   ├── Login ⭐
│   ├── Refresh Token
│   ├── Get User Details
│   ├── Update User Details
│   ├── List Users (ADMIN)
│   ├── Reset Password (ADMIN)
│   ├── Block User (ADMIN)
│   └── Unblock User (ADMIN)
│
├── 2. Roles (1 request)
│   └── Get All Roles
│
├── 3. Categories (4 requests)
│   ├── Get All Categories
│   ├── Create Category (ADMIN)
│   ├── Update Category (ADMIN)
│   └── Delete Category (ADMIN)
│
├── 4. Products (6 requests)
│   ├── Get All Products
│   ├── Get Product by ID
│   ├── Create Product (ADMIN)
│   ├── Update Product (ADMIN)
│   ├── Delete Product (ADMIN)
│   └── Upload Images (ADMIN)
│
├── 5. Orders (6 requests)
│   ├── Create Order (USER)
│   ├── Get Order by ID
│   ├── Get User Orders
│   ├── Search Orders (ADMIN)
│   ├── Update Order (ADMIN)
│   └── Delete Order (ADMIN)
│
├── 6. Comments (4 requests)
│   ├── Get Comments by Product
│   ├── Create Comment (USER)
│   ├── Update Comment (USER)
│   └── Delete Comment
│
└── 7. Coupons (1 request)
    └── Calculate Coupon
```

---

## 🎯 Testing Workflow

### Recommended Flow

```
1. Start Backend Server
   mvn spring-boot:run
   Wait for: "Started HolashopBackendApplication"
   
2. Import Postman Collection
   Import 2 JSON files
   
3. Select Environment
   "Holashop Environment"
   
4. Run Login Request
   "1.2. Login ⭐" → Token auto-saved
   
5. Test Other APIs
   All authenticated requests use token automatically
   
6. Use Swagger (Optional)
   http://localhost:8088/swagger-ui.html
   Copy token from Postman → Authorize
```

### Example Testing Sequence

```
Register User (1.1)
    ↓
Login (1.2) → Save tokens ✅
    ↓
Get User Details (1.4)
    ↓
Get All Products (4.1)
    ↓
Create Order (5.1)
    ↓
Get Order Details (5.2)
    ↓
Create Comment (6.2)
```

---

## 🛠️ Environment Variables

### Available Variables

| Variable | Default Value | Description |
|----------|---------------|-------------|
| `baseUrl` | `http://localhost:8088/api/v1` | API base URL |
| `access_token` | (auto-saved) | JWT token |
| `refresh_token` | (auto-saved) | Refresh token |
| `userId` | `1` | Test user ID |
| `productId` | `1` | Test product ID |
| `categoryId` | `1` | Test category ID |
| `orderId` | `1` | Test order ID |
| `commentId` | `1` | Test comment ID |
| `couponCode` | `TEST2024` | Test coupon code |
| `totalAmount` | `100000` | Test amount |

### Customize Variables

```bash
Postman → Environments → Holashop Environment
Edit "CURRENT VALUE" → Ctrl+S
```

---

## 🐛 Troubleshooting

### Lỗi: "Could not get response"

**Nguyên nhân:** Backend không chạy

**Giải pháp:**
```bash
cd holashop-be
mvn spring-boot:run
```

### Lỗi: 401 Unauthorized

**Nguyên nhân:** 
- Chưa login
- Token hết hạn

**Giải pháp:**
1. Chạy "1.2. Login" request
2. Hoặc chạy "1.3. Refresh Token"
3. Check Console logs (Ctrl+Alt+C)

### Lỗi: 403 Forbidden

**Nguyên nhân:** User không có quyền

**Giải pháp:**
- API cần ADMIN → Login với admin account
- API cần USER → Login với user account

### Token không được lưu

**Kiểm tra:**
1. Environment "Holashop Environment" đã được chọn?
2. Response có status 200?
3. Response JSON có `success: true`?
4. Check Postman Console (Ctrl+Alt+C)

**Fix:**
- Re-import collection
- Restart Postman

---

## 📚 API Reference

### Full API Documentation

Xem file: [api-list.md](../api-list.md)

### Base URL

```
http://localhost:8088/api/v1
```

### Authentication

```
Authorization: Bearer <access_token>
```

### Common Response Format

```json
{
  "success": true,
  "message": "Operation successful",
  "payload": { ... },
  "errors": []
}
```

---

## 💡 Pro Tips

### 1. Sử dụng Console Logs

```bash
Postman: Ctrl+Alt+C (Windows) / Cmd+Alt+C (Mac)
```

Xem:
- Request details
- Response data
- Script logs
- Errors

### 2. Collection Runner

Test nhiều requests cùng lúc:

```bash
Collection → Run collection
Select requests → Set iterations → Run
```

### 3. Environments cho nhiều môi trường

```bash
Duplicate "Holashop Environment"
Rename: "Holashop - Production"
Edit baseUrl: https://api.holashop.com/api/v1
```

### 4. Export Collection

```bash
Right-click collection → Export
Format: Collection v2.1
Share với team
```

### 5. Keyboard Shortcuts

| Action | Shortcut |
|--------|----------|
| Send request | `Ctrl+Enter` |
| Save | `Ctrl+S` |
| Console | `Ctrl+Alt+C` |
| New request | `Ctrl+N` |
| Search | `Ctrl+K` |

---

## 🔗 Links

### Documentation

- **Postman Guide:** [POSTMAN_GUIDE.md](POSTMAN_GUIDE.md)
- **API Reference:** [../api-list.md](../api-list.md)
- **Swagger UI:** http://localhost:8088/swagger-ui.html

### External Resources

- **Postman Learning:** https://learning.postman.com/
- **SpringDoc OpenAPI:** https://springdoc.org/
- **Newman (Postman CLI):** https://www.npmjs.com/package/newman

---

## 🎓 Next Steps

1. ✅ Import Postman collection & environment
2. ✅ Run Login request
3. ✅ Test all API endpoints
4. ⬜ Create test automation scripts
5. ⬜ Setup CI/CD with Newman
6. ⬜ Explore Swagger UI

---

## 📝 Notes

### Backend Requirements

```bash
# Database
MySQL 8.x running on port 3307

# Application
Spring Boot 3.x on port 8088

# Required data
Roles must be inserted (run migration V7)
```

### Token Expiration

- **Access Token:** 30 days (2592000 seconds)
- **Refresh Token:** 60 days (5184000 seconds)

**When expired:** Use "1.3. Refresh Token" request

---

## 🎉 Ready to Test!

**Quick Checklist:**
- [ ] ✅ Backend running (port 8088)
- [ ] ✅ Database running (MySQL)
- [ ] ✅ Roles inserted in database
- [ ] ✅ Postman collection imported
- [ ] ✅ Environment selected
- [ ] ✅ Login request executed
- [ ] ✅ Token saved
- [ ] ✅ Ready to test all APIs!

**Happy Testing!** 🚀
