# Docker Database CLI Commands

This document contains useful CLI commands for interacting with the databases (MySQL, Redis, Neo4j) used in the Holashop project running via Docker Compose.

**Note:** Ensure your containers are running (`docker-compose up -d`) before executing these commands.

## MySQL

*   **Container Name:** `mysql8-container`
*   **Database:** `holashop`
*   **User:** `root`
*   **Password:** `10062003` (Default from .env)

### 1. Access MySQL Shell
Connect to the MySQL CLI inside the container.
```bash
docker exec -it mysql8-container mysql -u root -p
# Enter password '10062003' when prompted
```
*Or with password in command (less secure for history):*
```bash
docker exec -it mysql8-container mysql -u root -p10062003 holashop
```

### 2. Backup Database (Dump)
Create a `.sql` dump file of the database on your host machine.
```bash
docker exec mysql8-container mysqldump -u root -p10062003 holashop > backup_holashop.sql
```

### 3. Restore Database
Restore the database from a `.sql` file on your host machine.
```bash
docker exec -i mysql8-container mysql -u root -p10062003 holashop < backup_holashop.sql
```

### 4. Run Specific SQL Query
Execute a single SQL query directly from your terminal.
```bash
docker exec mysql8-container mysql -u root -p10062003 holashop -e "SHOW TABLES;"
```

---

## Redis

*   **Container Name:** `redis-container`
*   **Port:** `6379` (Container), `6380` (Host)

### 1. Access Redis CLI
Connect to the Redis CLI inside the container.
```bash
docker exec -it redis-container redis-cli
```

### 2. Basic Redis Commands (inside CLI)
```bash
PING                  # Check connection (Returns PONG)
KEYS *                # List all keys (Use with caution in prod)
GET <key>             # Get value of a key
FLUSHALL              # Clear all data
```

### 3. Monitor Redis Commands
Watch real-time commands processed by the Redis server.
```bash
docker exec -it redis-container redis-cli monitor
```

---

## Neo4j

*   **Container Name:** `neo4j-container`
*   **Auth:** `neo4j/10062003` (Default from .env)
*   **Ports:** `7474` (HTTP), `7687` (Bolt)

### 1. Access Cypher Shell
Connect to the Neo4j Cypher Shell inside the container.
```bash
docker exec -it neo4j-container cypher-shell -u neo4j -p 10062003
```

### 2. Backup Neo4j Data
Neo4j backups usually require the enterprise edition or stopping the database to copy the data folder. Since the volume is mapped to `./neo4j` on the host, you can simply backup that directory on your host machine while the container is stopped.

```bash
# Stop the container
docker stop neo4j-container

# Copy the data folder (Powershell example)
Copy-Item -Path ./neo4j -Destination ./neo4j_backup -Recurse

# Restart the container
docker start neo4j-container
```

### 3. Run Cypher Query
Execute a Cypher query from the terminal.
```bash
docker exec neo4j-container cypher-shell -u neo4j -p 10062003 "MATCH (n) RETURN count(n);"
```
