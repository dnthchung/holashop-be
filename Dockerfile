FROM openjdk:17
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package -DskipTests
CMD ["./mvnw", "spring-boot:run"]

# Pull and start/run a DockerHub container
# -d: Chạy container ở chế độ nền (detach mode)
# -p 8080:8080: Chuyển tiếp cổng 8080 từ container sang cổng 8080 trên máy tính host
# -v "$(pwd):/app": Mount thư mục hiện tại của máy tính host vào /app trong container
# docker run -dp 8080:8080 --name shopapp-backend -v "$(pwd):/app" --network shopapp-network hoangtien2k3/shopapp-backend:v1.0.0
