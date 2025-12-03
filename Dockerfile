# 第一阶段：使用Maven和JDK 21构建应用
FROM maven:3.9-amazoncorretto-21-alpine AS build
WORKDIR /health-check-in
COPY pom.xml .
# 先下载依赖（利用Docker缓存层）
RUN mvn dependency:go-offline

COPY src ./src
# 打包应用
RUN mvn package -DskipTests

# 第二阶段：使用JDK 21运行应用
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /health-check-in
# 从构建阶段复制生成的jar包
COPY --from=build /health-check-in/target/*.jar health-check-in.jar
# 暴露端口
EXPOSE 8088
# 启动应用
ENTRYPOINT ["java", "-jar", "health-check-in.jar"]