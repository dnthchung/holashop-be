# 🧪 API Testing Guide

> Quick navigation cho API testing tools

---

## 📍 Quick Links

| What | Where | How |
|------|-------|-----|
| **Postman Collection** | `document/api-test/` | Import JSON files |
| **Swagger UI** | http://localhost:8088/swagger-ui.html | Web browser |
| **API Reference** | `document/api-list.md` | Documentation |

---

## 🚀 Quick Start

### 1. Postman (Recommended for API Testing)

```bash
# Navigate to folder
cd document/api-test/

# Files to import:
✅ Holashop.postman_collection.json
✅ Holashop.postman_environment.json

# Full guide:
📖 README.md
📖 POSTMAN_GUIDE.md
```

**Steps:**
1. Import 2 JSON files vào Postman
2. Select "Holashop Environment"
3. Run "1.2. Login ⭐" request
4. Token auto-saved → Test other APIs!

**Documentation:** [document/api-test/README.md](document/api-test/README.md)

---

### 2. Swagger UI (Interactive Documentation)

```bash
# Start backend first
mvn spring-boot:run

# Then open browser
http://localhost:8088/swagger-ui.html
```

**Features:**
- ✅ Interactive documentation
- ✅ Try-it-out functionality
- ✅ Auto-generated from code

**Authentication:**
1. Get token from Postman (run Login first)
2. Click "Authorize" button in Swagger
3. Enter: `Bearer <your-token>`
4. Test authenticated endpoints

---

## 📁 File Structure

```
holashop-be/
├── document/
│   └── api-test/                          ← All Postman files here
│       ├── Holashop.postman_collection.json
│       ├── Holashop.postman_environment.json
│       ├── POSTMAN_GUIDE.md
│       ├── README.md
│       └── CHANGELOG.md
│
├── API_TESTING.md                         ← This file (quick nav)
└── README.md                              ← Project README
```

---

## 🔐 Authentication

### Login Flow

```
1. Register (optional)
   POST /api/v1/users/register
   
2. Login ⭐
   POST /api/v1/users/login
   → Token auto-saved in Postman environment
   
3. Use token
   All requests: Authorization: Bearer {{access_token}}
   
4. Refresh when expired
   POST /api/v1/users/refresh-token
```

---

## 📊 API Categories

### 35+ Endpoints organized by feature:

| Category | Endpoints | Auth Required |
|----------|-----------|---------------|
| **Authentication** | Register, Login, Refresh | Public |
| **Users** | CRUD, Management | ✅ USER/ADMIN |
| **Roles** | List | Public |
| **Categories** | CRUD | ADMIN (write) |
| **Products** | CRUD, Upload | ADMIN (write) |
| **Orders** | CRUD, Search | ✅ USER/ADMIN |
| **Comments** | CRUD | USER (write) |
| **Coupons** | Calculate | Public |

**Full list:** [document/api-list.md](document/api-list.md)

---

## 🛠️ Prerequisites

### Backend Setup

```bash
# 1. Database
docker-compose up -d mysql

# 2. Insert roles (required!)
# Restart app để Flyway chạy migration V7

# 3. Start backend
mvn spring-boot:run

# Wait for: "Started HolashopBackendApplication"
```

### Testing Tools

```bash
# Postman
Download: https://www.postman.com/downloads/

# Browser (for Swagger)
Any modern browser
```

---

## 💡 Pro Tips

1. **Always run Login first** - Token được auto-save
2. **Use Postman Console** - Ctrl+Alt+C để debug
3. **Check Environment** - Eye icon để xem variables
4. **Swagger for quick tests** - No need to setup requests
5. **Collection Runner** - Test multiple requests cùng lúc

---

## 🐛 Common Issues

| Issue | Solution |
|-------|----------|
| Connection refused | Start backend: `mvn spring-boot:run` |
| 401 Unauthorized | Run Login request first |
| 403 Forbidden | Login với correct role (USER/ADMIN) |
| Token not saved | Check environment selected |
| Swagger not working | Backend phải đang chạy |

---

## 📚 Full Documentation

- **API Testing:** [document/api-test/README.md](document/api-test/README.md)
- **Postman Guide:** [document/api-test/POSTMAN_GUIDE.md](document/api-test/POSTMAN_GUIDE.md)
- **API Reference:** [document/api-list.md](document/api-list.md)
- **Run Instructions:** [RUN_INSTRUCTIONS.md](RUN_INSTRUCTIONS.md)

---

## 🎉 Ready to Go!

**Choose your tool:**

→ **Postman** (Full testing): Import from `document/api-test/`  
→ **Swagger** (Quick test): http://localhost:8088/swagger-ui.html

**Both work great!** 🚀

---

**Last Updated:** 2026-01-25  
**Version:** 2.0.0
