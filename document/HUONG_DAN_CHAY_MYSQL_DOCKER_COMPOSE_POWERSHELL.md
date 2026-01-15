# Hướng dẫn chạy MySQL bằng Docker Compose (PowerShell) – hola-shop-be

Tài liệu này hướng dẫn **chạy lại từ đầu** (fresh) MySQL bằng Docker Compose trên **Windows PowerShell**, và import toàn bộ database (schema + data) từ file SQL chuẩn trong dự án.

---

## 0) Yêu cầu trước khi chạy

- Đã cài **Docker Desktop**
- Đã bật Docker Engine (Docker Desktop đang chạy)
- Đang đứng đúng thư mục dự án có `docker-compose.yml` và `.env`

Ví dụ:

```powershell
PS D:\github-personal\hola-shop-be>
```

---

## 1) Kiểm tra file cấu hình

### 1.1 `.env` (ví dụ)

Đảm bảo `.env` nằm **cùng thư mục** với `docker-compose.yml` và có các thông số DB khớp với file sql (MySQL 8+, database `hola_shop`):

```env
MYSQL_TAG=8.2.0
MYSQL_DATABASE=hola_shop
MYSQL_ROOT_PASSWORD=10062003
MYSQL_PORT=3307
```

> **Lưu ý**: File setup dữ liệu (`data_sql.sql`) đã bao gồm lệnh `CREATE DATABASE hola_shop` và `USE hola_shop`, vì vậy tên database trong `.env` chỉ mang tính chất khởi tạo ban đầu cho container, quan trọng là Port và Password.

### 1.2 `docker-compose.yml` (MySQL-only)

Đảm bảo service mysql đang map đúng port và volume:

```yml
services:
  mysql8-container:
    image: mysql:${MYSQL_TAG}
    # ...
    ports:
      - "${MYSQL_PORT}:3306"
    volumes:
      - mysql-data:/var/lib/mysql
    # ...
```

---

## 2) Reset sạch (xóa container + volume) để chạy lại từ đầu

Chạy các lệnh sau trong **PowerShell** tại thư mục dự án:

```powershell
PS D:\github-personal\hola-shop-be> docker compose down -v --remove-orphans
```

- `-v` là quan trọng: xóa luôn **volume DB cũ** để MySQL init lại từ đầu, tránh xung đột dữ liệu cũ.

---

## 3) Start MySQL container

Chạy:

```powershell
PS D:\github-personal\hola-shop-be> docker compose up -d
```

Chờ khoảng 10-20 giây để MySQL khởi động hoàn tất. Bạn có thể check log để chắc chắn nó đã "ready for connections":

```powershell
PS D:\github-personal\hola-shop-be> docker logs --tail 20 mysql8-container
```

---

## 4) Import Schema và Data (Sử dụng `data_sql.sql`)

Chúng ta sẽ dùng file **`src/main/resources/sql/data_sql.sql`**. File này là bản dump đầy đủ (Full Dump) chứa cả **Schema** (Create Table) và **Data** (Insert), đồng thời tự động tạo DB `hola_shop` nếu chưa có.

**Lệnh Import trên PowerShell:**

Sử dụng `Get-Content` pipe vào `docker exec`. Lưu ý **không** cần chỉ định tên database trong lệnh `mysql` vì trong file SQL đã có lệnh `USE hola_shop;`.

```powershell
PS D:\github-personal\hola-shop-be> Get-Content -Raw "src\main\resources\sql\data_sql.sql" | docker exec -i mysql8-container mysql -uroot -p10062003
```

> **Giải thích**:
> - `-Raw`: Đọc toàn bộ file thành một chuỗi (giữ nguyên xuống dòng), tránh lỗi cú pháp khi pipe.
> - `-i`: Interactive mode cho docker exec để nhận input từ pipe.
> - `-u... -p...`: User và Password (viết liền sau -p).

---

## 5) Verify (Kiểm tra kết quả)

Sau khi import xong, hãy kiểm tra xem database và bảng đã có chưa.

### 5.1 Kiểm tra danh sách bảng

```powershell
PS D:\github-personal\hola-shop-be> docker exec -it mysql8-container mysql -uroot -p10062003 -e "USE hola_shop; SHOW TABLES;"
```

Bạn sẽ thấy danh sách các bảng như: `categories`, `products`, `users`, `orders`, `order_details`, v.v.

### 5.2 Kiểm tra dữ liệu (ví dụ bảng Products)

Đếm số lượng sản phẩm để chắc chắn data đã vào:

```powershell
PS D:\github-personal\hola-shop-be> docker exec -it mysql8-container mysql -uroot -p10062003 -e "USE hola_shop; SELECT COUNT(*) FROM products;"
```

Nếu kết quả > 0 (ví dụ vài chục hoặc vài trăm bản ghi), chúc mừng bạn đã setup thành công!

---

## 6) Troubleshooting (Gỡ lỗi)

### 6.1 Lỗi `ERROR 1045 Access denied`
- Kiểm tra lại password trong lệnh docker exec có khớp với `.env` không.
- Nếu vừa đổi password trong `.env`, bắt buộc phải chạy `docker compose down -v` để reset volume thì pass mới mới có hiệu lực.

### 6.2 Lỗi `Unknown database 'hola_shop'` khi chưa import
- Container MySQL khi khởi tạo lần đầu sẽ tạo DB theo biến `MYSQL_DATABASE` trong `.env`. Hãy đảm bảo `.env` có `MYSQL_DATABASE=hola_shop`.
- Tuy nhiên, file `data_sql.sql` của chúng ta có lệnh `CREATE DATABASE IF NOT EXISTS hola_shop`, nên nếu chạy đúng lệnh import ở bước 4 thì lỗi này sẽ tự hết.

### 6.3 Lỗi encoding / ký tự lạ khi import
- PowerShell đôi khi bị lỗi encoding với ký tự có dấu.
- Nếu gặp lỗi này, hãy thử thêm encoding cho `Get-Content`:
  ```powershell
  Get-Content -Raw -Encoding UTF8 "src\main\resources\sql\data_sql.sql" | ...
  ```
