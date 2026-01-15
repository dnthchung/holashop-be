# Hướng dẫn chạy MySQL bằng Docker Compose (PowerShell) – hola-shop-be

Tài liệu này hướng dẫn **chạy lại từ đầu** (fresh) MySQL bằng Docker Compose trên **Windows PowerShell**, import schema và import data từ các file SQL trong dự án.

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

Đảm bảo `.env` nằm **cùng thư mục** với `docker-compose.yml` và có tối thiểu:

```env
MYSQL_TAG=8.2.0
MYSQL_DATABASE=hola_shop
MYSQL_ROOT_PASSWORD=10062003
MYSQL_PORT=3307
```

> Ghi chú: MySQL official image **không dùng** `MYSQL_ROOT_USERNAME`. Root user luôn là `root`.

### 1.2 `docker-compose.yml` (MySQL-only)

Ví dụ tối thiểu:

```yml
services:
  mysql8-container:
    container_name: mysql8-container
    image: mysql:${MYSQL_TAG}
    restart: always
    environment:
      MYSQL_DATABASE: ${MYSQL_DATABASE}
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
    ports:
      - "${MYSQL_PORT}:3306"
    volumes:
      - mysql-data:/var/lib/mysql
    networks:
      - hola-shop-network

networks:
  hola-shop-network:
    name: hola-shop-network
    driver: bridge

volumes:
  mysql-data:
```

---

## 2) Reset sạch (xóa container + volume + network) để chạy lại từ đầu

Chạy các lệnh sau trong **PowerShell** tại thư mục dự án:

```powershell
PS D:\github-personal\hola-shop-be> docker compose down -v --remove-orphans
```

- `-v` là quan trọng: xóa luôn **volume DB** để MySQL init lại từ đầu.

> Nếu bạn muốn kiểm tra volume còn hay không:

```powershell
PS D:\github-personal\hola-shop-be> docker volume ls | findstr mysql
```

> Nếu cần xóa volume thủ công (hiếm khi cần):

```powershell
PS D:\github-personal\hola-shop-be> docker volume rm hola-shop-be_mysql-data
```

---

## 3) Xác nhận Docker Compose đã đọc đúng `.env`

Chạy:

```powershell
PS D:\github-personal\hola-shop-be> docker compose config
```

Bạn cần chắc chắn các giá trị quan trọng đã được “render” đúng:

- `image: mysql:<MYSQL_TAG>`
- `MYSQL_DATABASE: <MYSQL_DATABASE>`
- `MYSQL_ROOT_PASSWORD: "<MYSQL_ROOT_PASSWORD>"`
- `published: "<MYSQL_PORT>"`

---

## 4) Start MySQL container

Chạy:

```powershell
PS D:\github-personal\hola-shop-be> docker compose up -d
```

---

## 5) Kiểm tra log MySQL (đảm bảo DB sẵn sàng nhận kết nối)

Xem log (đuôi 80 dòng):

```powershell
PS D:\github-personal\hola-shop-be> docker logs --tail 80 mysql8-container
```

Hoặc xem realtime (nhấn `Ctrl + C` để thoát):

```powershell
PS D:\github-personal\hola-shop-be> docker logs -f mysql8-container
```

---

## 6) Test kết nối MySQL trong container (khuyến nghị test nhanh bằng `-e`)

```powershell
PS D:\github-personal\hola-shop-be> docker exec -it mysql8-container mysql -uroot -p10062003 -e "SHOW DATABASES;"
```

> Lưu ý: `-p<password>` viết liền nhau, không có dấu cách.

---

## 7) Import schema và data từ file SQL (PowerShell)

PowerShell **không dùng** cú pháp `< file.sql` như bash. Dùng `Get-Content` + pipe.

### 7.1 Import schema (DDL)

File schema trong dự án:

- `D:\github-personal\hola-shop-be\src\main\resources\db\migration\V1__init_schema.sql`

Chạy:

```powershell
PS D:\github-personal\hola-shop-be> Get-Content -Raw "D:\github-personal\hola-shop-be\src\main\resources\db\migration\V1__init_schema.sql" |
>>   docker exec -i mysql8-container mysql -uroot -p10062003 hola_shop
```

### 7.2 Import dữ liệu mẫu (DML)

Các file data trong dự án:

- `D:\github-personal\hola-shop-be\src\main\resources\sql\data_sql.sql`
- `D:\github-personal\hola-shop-be\src\main\resources\sql\database.sql`

Chạy lần lượt (nếu cả hai file đều có data):

```powershell
PS D:\github-personal\hola-shop-be> Get-Content -Raw "D:\github-personal\hola-shop-be\src\main\resources\sql\database.sql" |
>>   docker exec -i mysql8-container mysql -uroot -p10062003 hola_shop
```

```powershell
PS D:\github-personal\hola-shop-be> Get-Content -Raw "D:\github-personal\hola-shop-be\src\main\resources\sql\data_sql.sql" |
>>   docker exec -i mysql8-container mysql -uroot -p10062003 hola_shop
```

---

## 8) Verify đã tạo bảng và dữ liệu chưa

Liệt kê bảng:

```powershell
PS D:\github-personal\hola-shop-be> docker exec -it mysql8-container mysql -uroot -p10062003 -e "USE hola_shop; SHOW TABLES;"
```

Sau đó chọn một bảng bất kỳ từ kết quả và đếm bản ghi (ví dụ bảng `users`):

```powershell
PS D:\github-personal\hola-shop-be> docker exec -it mysql8-container mysql -uroot -p10062003 -e "USE hola_shop; SELECT COUNT(*) FROM users;"
```

---

## 9) Troubleshooting nhanh

### 9.1 `ERROR 1045 Access denied for user 'root'`

- Password không đúng, hoặc MySQL chưa init theo password mới do volume cũ.
- Giải pháp: chạy lại **Bước 2** (down -v) rồi up lại.

### 9.2 Không thấy DB `hola_shop`

- Kiểm tra `.env` và `docker compose config` xem `MYSQL_DATABASE` có đúng không.
- Đảm bảo `.env` nằm cùng thư mục với `docker-compose.yml`.

### 9.3 Port bị chiếm (không start được)

- Đổi `MYSQL_PORT` sang cổng khác (vd: 3308) trong `.env`.
- Up lại: `docker compose up -d`

---

## 10) (Tuỳ chọn) Kết nối từ tool ngoài (DBeaver / IntelliJ / MySQL Workbench)

- Host: `localhost`
- Port: `3307` (theo `MYSQL_PORT`)
- User: `root`
- Password: `10062003`
- Database: `hola_shop`

---
