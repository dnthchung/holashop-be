# How to Run Holashop Backend

This guide provides step-by-step instructions to set up and run the Holashop Backend application locally.

## Prerequisites

Ensure you have the following installed on your machine:
- **Java JDK 17** or higher
- **Maven** (or use the provided `mvnw` wrapper)
- **Docker** & **Docker Compose**

### ⚠️ Important: PowerShell Command Execution

**PowerShell does NOT automatically search the current directory** for executables (unlike Command Prompt). This is a security feature.

**Always use `.\` prefix in PowerShell:**
- ✅ Correct: `.\mvnw.cmd spring-boot:run`
- ❌ Wrong: `mvnw.cmd spring-boot:run` (will cause `CommandNotFoundException`)

**Why?** PowerShell only searches in `$PATH` environment variable, not the current directory. The `.\` prefix explicitly tells PowerShell to look in the current directory.

## 1. Environment Configuration

The project uses a `.env` file to configure environment variables for Docker containers.
Check the `.env` file in the root directory. The default configuration is set to work out-of-the-box:

- **MySQL Port:** 3307
- **Redis Port:** 6380
- **Neo4j Ports:** 7474 (HTTP), 7687 (Bolt)

*Note: The `application.yml` file is configured to connect to these ports on `localhost`.*

## 2. Start Infrastructure Services

Use Docker Compose to start the required databases (MySQL, Redis, Neo4j).

```bash
docker-compose up -d
```

Verify that the containers are running:
```bash
docker ps
```
You should see `mysql8-container`, `redis-container`, and `neo4j-container` running.

## 3. Database Setup

### Create Database (Required First Step)
Before running the application, you need to create the database. The database name must exist, but Flyway will create all tables automatically.

```bash
# Create the holashop database
docker exec -it mysql8-container mysql -uroot -p10062003 -e "CREATE DATABASE IF NOT EXISTS holashop CHARACTER SET utf8mb4;"
```

### Automatic Migration (Flyway)
The application uses **Flyway** to manage database migrations. When you start the Spring Boot application, it will automatically:
1. Create all database tables and schema (via `V0__init_schema.sql`)
2. Apply all subsequent migrations (V2, V3, V4, V5, V6)

**Note:** You don't need to manually create tables - Flyway handles this automatically. However, the database itself must be created first (see step above).

### Manual Data Seeding (Optional)
If you need to seed the database with demo data, you can manually execute the provided SQL scripts:
- **Demo Data:** `sql/shopapp.sql/data_demo.sql` (located at project root)
- **Other scripts:** `src/main/resources/data_sql.sql`

**Example command to import demo data:**
```bash
# Windows PowerShell
Get-Content sql\shopapp.sql\data_demo.sql | docker exec -i mysql8-container mysql -uroot -p10062003 --default-character-set=utf8mb4 holashop

# Or import data_sql.sql
Get-Content src\main\resources\data_sql.sql | docker exec -i mysql8-container mysql -uroot -p10062003 --default-character-set=utf8mb4 holashop
```

You can connect to the database using a client (like DBeaver or MySQL Workbench):
- **Host:** localhost
- **Port:** 3307
- **Database:** holashop
- **Username:** root
- **Password:** 10062003 (as defined in `.env`)

## 4. Build the Application

Navigate to the project root and build the application using Maven:

```bash
# Using local Maven (if installed)
mvn clean install

# OR using Maven Wrapper (Windows PowerShell) - RECOMMENDED
.\mvnw.cmd clean install

# OR using Maven Wrapper (Windows Command Prompt)
mvnw.cmd clean install

# OR using Maven Wrapper (Linux/Mac)
./mvnw clean install
```

**Note:** After building, you'll find the JAR file at: `target/holashop-backend-0.0.1-SNAPSHOT.jar`

## 5. Run the Application

**Before running, ensure:**
1. ✅ Docker containers are running (`docker ps`)
2. ✅ Database `holashop` exists (created in step 3)

Start the Spring Boot application:

```bash
# Using local Maven (if installed)
mvn spring-boot:run

# OR using Maven Wrapper (Windows PowerShell) - RECOMMENDED
.\mvnw.cmd spring-boot:run

# OR using Maven Wrapper (Windows Command Prompt)
mvnw.cmd spring-boot:run

# OR using Maven Wrapper (Linux/Mac)
./mvnw spring-boot:run
```

**Alternative: Run from JAR file (after building)**
```bash
# Windows PowerShell
java -jar target\holashop-backend-0.0.1-SNAPSHOT.jar

# Linux/Mac
java -jar target/holashop-backend-0.0.1-SNAPSHOT.jar
```

**What happens when you run:**
1. Spring Boot starts the application
2. Flyway automatically runs database migrations (V0 → V2 → V3 → V4 → V5 → V6)
3. Application connects to MySQL (port 3307) and Redis (port 6380)
4. Server starts on port 8088

## 6. Access the Application

Once the application is running, you can access:

- **API Base URL:** `http://localhost:8088/api/v1`
- **Swagger UI (API Documentation):** `http://localhost:8088/swagger-ui.html`
- **Actuator Health:** `http://localhost:8088/api/v1/actuator/health`

*Note: The application port is configured to `8088` in `application.yml`.*

## Troubleshooting

- **Port Conflicts:** If ports 3307, 6380, or 8088 are already in use, update the `.env` file and/or `application.yml`.
- **Database Connection:** Ensure the Docker containers are running (`docker ps`). If Flyway fails on checksums, it usually means a local migration file was changed after being applied. You may need to repair Flyway history or reset the database.
- **Flyway Migration Errors:** If you encounter migration errors (e.g., "Table doesn't exist"), you may need to reset the database:
  ```bash
  # Drop and recreate database
  docker exec -it mysql8-container mysql -uroot -p10062003 -e "DROP DATABASE IF EXISTS holashop;"
  docker exec -it mysql8-container mysql -uroot -p10062003 -e "CREATE DATABASE holashop CHARACTER SET utf8mb4;"
  
  # Then restart the application - Flyway will run migrations from V0
  ```
- **Maven Not Found / CommandNotFoundException:** 
  - **PowerShell:** Always use `.\mvnw.cmd` (with `.\` prefix)
  - **Command Prompt:** You can use `mvnw.cmd` or `.\mvnw.cmd`
  - **Linux/Mac:** Use `./mvnw`
  - **Error message:** `The term 'mvnw.cmd' is not recognized` means you forgot the `.\` prefix in PowerShell
- **Build Successful but App Won't Start:** 
  - Check Docker containers: `docker ps`
  - Verify database exists: `docker exec -it mysql8-container mysql -uroot -p10062003 -e "SHOW DATABASES;"`
  - Check port 8088 is not in use: `netstat -ano | findstr :8088` (Windows) or `lsof -i :8088` (Linux/Mac)
