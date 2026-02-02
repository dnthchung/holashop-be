# 🚀 Hướng dẫn sử dụng Postman với Holashop API

## 📦 Files cần import

1. **Holashop.postman_collection.json** - Collection chứa tất cả API endpoints
2. **Holashop.postman_environment.json** - Environment variables

---

## 📥 Bước 1: Import vào Postman

### Cách 1: Import từ file

1. Mở Postman
2. Click **Import** (góc trên bên trái)
3. Kéo thả 2 files JSON vào cửa sổ Import:
   - `Holashop.postman_collection.json`
   - `Holashop.postman_environment.json`
4. Click **Import**

### Cách 2: Import từ GitHub (nếu có)

1. Click **Import** → **Link**
2. Paste URL của file JSON từ GitHub
3. Click **Continue** → **Import**

---

## ⚙️ Bước 2: Chọn Environment

1. Tại dropdown góc phải trên (mặc định là "No Environment")
2. Chọn **"Holashop Environment"**
3. Environment đã được active ✅

---

## 🔑 Bước 3: Login để lấy Token

**QUAN TRỌNG:** Phải chạy bước này trước!

1. Mở folder **"1. Authentication & Users"**
2. Click request **"1.2. Login ⭐"**
3. Click **Send**

**Kết quả:**
- ✅ Nếu thành công, token sẽ **TỰ ĐỘNG** được lưu vào environment
- Xem tab **Console** (Ctrl+Alt+C) để thấy logs:
  ```
  ✅ Login successful!
  🔑 Access token saved: eyJhbGci...
  🔄 Refresh token saved: dfcb5ada...
  ```

**Kiểm tra token:**
1. Click vào **Environment quick look** (con mắt góc phải trên)
2. Bạn sẽ thấy `access_token` và `refresh_token` đã có giá trị

---

## 🎯 Bước 4: Test các API khác

Sau khi login, tất cả API đều tự động dùng token:

### Ví dụ: Get User Details

1. Click request **"1.4. Get User Details"**
2. Xem tab **Authorization**:
   - Type: Bearer Token
   - Token: `{{access_token}}` ← Tự động lấy từ environment
3. Click **Send**
4. Success! 🎉

---

## 📁 Cấu trúc Collection

```
Holashop API
├── 1. Authentication & Users
│   ├── 1.1. Register
│   ├── 1.2. Login ⭐ (Chạy đầu tiên!)
│   ├── 1.3. Refresh Token
│   ├── 1.4. Get User Details
│   ├── 1.5. Update User Details
│   ├── 1.6. List Users (ADMIN)
│   ├── 1.7. Reset Password (ADMIN)
│   ├── 1.8. Block User (ADMIN)
│   └── 1.9. Unblock User (ADMIN)
├── 2. Roles
│   └── 2.1. Get All Roles
├── 3. Categories
│   ├── 3.1. Get All Categories
│   ├── 3.2. Create Category (ADMIN)
│   ├── 3.3. Update Category (ADMIN)
│   └── 3.4. Delete Category (ADMIN)
├── 4. Products
│   ├── 4.1. Get All Products
│   ├── 4.2. Get Product by ID
│   ├── 4.3. Create Product (ADMIN)
│   ├── 4.4. Update Product (ADMIN)
│   ├── 4.5. Delete Product (ADMIN)
│   └── 4.6. Upload Product Images (ADMIN)
├── 5. Orders
│   ├── 5.1. Create Order (USER)
│   ├── 5.2. Get Order by ID
│   ├── 5.3. Get User Orders
│   ├── 5.4. Search Orders (ADMIN)
│   ├── 5.5. Update Order (ADMIN)
│   └── 5.6. Delete Order (ADMIN)
├── 6. Comments
│   ├── 6.1. Get Comments by Product
│   ├── 6.2. Create Comment (USER)
│   ├── 6.3. Update Comment (USER)
│   └── 6.4. Delete Comment (USER/ADMIN)
└── 7. Coupons
    └── 7.1. Calculate Coupon
```

---

## 🔐 Cơ chế Auto-save Token

### Login Request có Test Script:

```javascript
// Auto-save tokens to environment variables
if (pm.response.code === 200) {
    const jsonData = pm.response.json();
    
    if (jsonData.success && jsonData.payload) {
        // Save to environment
        pm.environment.set('access_token', jsonData.payload.token);
        pm.environment.set('refresh_token', jsonData.payload.refresh_token);
        
        console.log('✅ Login successful!');
        console.log('🔑 Access token saved');
    }
}
```

### Authenticated Requests tự động dùng token:

```
Authorization: Bearer {{access_token}}
```

Postman tự động thay `{{access_token}}` bằng giá trị thật từ environment!

---

## 🔄 Khi Token hết hạn

Token mặc định hết hạn sau **30 ngày**. Khi expire:

### Option 1: Refresh Token

1. Chạy request **"1.3. Refresh Token"**
2. Token mới sẽ tự động được lưu
3. Continue testing với token mới

### Option 2: Login lại

1. Chạy lại **"1.2. Login"**
2. Token mới được tạo và lưu

---

## 🛠️ Customize Environment Variables

Click vào **Environments** (sidebar trái) → **Holashop Environment**:

| Variable | Default Value | Description |
|----------|---------------|-------------|
| `baseUrl` | `http://localhost:8088/api/v1` | API base URL |
| `access_token` | (auto-saved) | JWT access token |
| `refresh_token` | (auto-saved) | Refresh token |
| `userId` | `1` | Test user ID |
| `productId` | `1` | Test product ID |
| `categoryId` | `1` | Test category ID |
| `orderId` | `1` | Test order ID |
| `commentId` | `1` | Test comment ID |
| `couponCode` | `TEST2024` | Test coupon code |
| `totalAmount` | `100000` | Test order amount |

**Cách sửa:**
1. Click vào variable cần sửa
2. Edit **CURRENT VALUE**
3. Ctrl+S để save

---

## 📊 Xem Console Logs

Mở Console để debug:

**Shortcut:** `Ctrl+Alt+C` (Windows) hoặc `Cmd+Alt+C` (Mac)

**Logs bạn sẽ thấy:**
```
🌍 Environment: Holashop Environment
🔗 Base URL: http://localhost:8088/api/v1
✅ Login successful!
🔑 Access token saved: eyJhbGci...
⏱️ Response time: 245ms
📊 Status: 200 OK
```

---

## 🎯 Testing Workflow

### Workflow chuẩn:

```
1. Start Backend Server (port 8088)
   ↓
2. Import Collection & Environment
   ↓
3. Select "Holashop Environment"
   ↓
4. Run "1.2. Login"
   ↓
5. Token auto-saved ✅
   ↓
6. Test các API khác (tự động dùng token)
   ↓
7. Success! 🎉
```

### Example Testing Flow:

```
Register User (1.1)
    ↓
Login (1.2) → Save tokens
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

## 🐛 Troubleshooting

### Lỗi: "Could not get response"

**Nguyên nhân:** Backend không chạy

**Giải pháp:**
```bash
# Start backend
cd holashop-be
mvn spring-boot:run
```

Đợi thấy: `Started HolashopBackendApplication`

### Lỗi: 401 Unauthorized

**Nguyên nhân:** 
- Chưa login
- Token hết hạn
- Token không được lưu

**Giải pháp:**
1. Chạy **"1.2. Login"** trước
2. Kiểm tra Console logs
3. Kiểm tra Environment variables (con mắt)
4. Nếu token expired → Chạy **"1.3. Refresh Token"**

### Lỗi: 403 Forbidden

**Nguyên nhân:** User không có quyền (ADMIN vs USER)

**Giải pháp:**
- API cần ADMIN → Login với account admin
- API cần USER → Login với account user thường

### Token không được auto-save

**Kiểm tra:**
1. Response có status 200?
2. Response JSON có `success: true`?
3. Response có `payload.token`?
4. Xem tab **Tests** trong request
5. Check Console logs (Ctrl+Alt+C)

**Fix:**
- Đảm bảo "Holashop Environment" được chọn
- Re-import collection nếu cần

---

## 💡 Pro Tips

### 1. Sử dụng Collection Runner

Test nhiều requests cùng lúc:

1. Click **Collection** → **Run collection**
2. Chọn requests cần test
3. Set **Iterations** (số lần chạy)
4. Click **Run Holashop API**

### 2. Tạo nhiều Environments

Ví dụ: Local, Staging, Production

1. Duplicate "Holashop Environment"
2. Đổi tên: "Holashop - Production"
3. Sửa `baseUrl`: `https://api.holashop.com/api/v1`
4. Switch environment khi cần

### 3. Export Collection để share

1. Right-click collection
2. **Export**
3. Format: Collection v2.1
4. Share file JSON với team

### 4. Keyboard Shortcuts

| Action | Shortcut |
|--------|----------|
| Send request | `Ctrl+Enter` |
| Save request | `Ctrl+S` |
| Open Console | `Ctrl+Alt+C` |
| New request | `Ctrl+N` |
| Search | `Ctrl+K` |

---

## 🎓 Next Steps

1. ✅ Import collection & environment
2. ✅ Run Login to save token
3. ✅ Test all endpoints
4. ⬜ Create test scripts for automation
5. ⬜ Setup CI/CD with Newman (Postman CLI)
6. ⬜ Share collection with team

---

## 📚 Tài liệu tham khảo

- **Postman Learning Center:** https://learning.postman.com/
- **Postman Scripts:** https://learning.postman.com/docs/writing-scripts/intro-to-scripts/
- **Newman CLI:** https://www.npmjs.com/package/newman
- **API Documentation:** `api-list.md` trong project

---

## 🆘 Cần giúp đỡ?

1. **Check Console logs** (Ctrl+Alt+C)
2. **Đọc request Description** (tab bên phải)
3. **Xem API docs:** `api-list.md`
4. **Check backend logs** trong terminal

---

## 🎉 Happy Testing!

**Quick Checklist:**
- [ ] ✅ Imported collection
- [ ] ✅ Imported environment  
- [ ] ✅ Selected environment
- [ ] ✅ Backend running (port 8088)
- [ ] ✅ Database running (MySQL)
- [ ] ✅ Ran Login request
- [ ] ✅ Token auto-saved
- [ ] ✅ Ready to test!

**Remember:** Always run **"1.2. Login ⭐"** first!
