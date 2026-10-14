FROM openjdk:8-jre-alpine
# 后端运行镜像：jar 由本地 mvn package 产出（见 docker-compose.yml 注释）
WORKDIR /app
COPY target/springboot05qmpxx3-0.0.1-SNAPSHOT.jar app.jar
# 上传目录
RUN mkdir -p /app/upload
# 时区
RUN apk add --no-cache tzdata && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo "Asia/Shanghai" > /etc/timezone
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar","--server.port=8080"]
