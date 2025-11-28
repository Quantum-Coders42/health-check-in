#!/bin/bash

# 部署验证脚本
# 用于验证新部署的容器是否正常运行

CONTAINER_NAME="health-check-in"
HEALTH_CHECK_URL="http://localhost:8080/actuator/health"  # Spring Boot健康检查端点
MAX_RETRIES=30
RETRY_INTERVAL=10

echo "Starting deployment validation for container: ${CONTAINER_NAME}"

# 检查容器是否正在运行
echo "Checking if container is running..."
if ! docker ps --format "table {{.Names}}" | grep -q "^${CONTAINER_NAME}$"; then
    echo "❌ ERROR: Container ${CONTAINER_NAME} is not running!"
    exit 1
fi

echo "✅ Container is running"

# 检查容器状态
echo "Checking container status..."
CONTAINER_STATUS=$(docker inspect -f '{{.State.Status}}' ${CONTAINER_NAME})
if [ "${CONTAINER_STATUS}" != "running" ]; then
    echo "❌ ERROR: Container status is ${CONTAINER_STATUS}, expected 'running'"
    exit 1
fi

echo "✅ Container status is running"

# 等待应用启动
echo "Waiting for application to start..."
sleep 20

# 健康检查
echo "Performing health check..."
RETRY_COUNT=0
while [ $RETRY_COUNT -lt $MAX_RETRIES ]; do
    if curl -s -f ${HEALTH_CHECK_URL} > /dev/null 2>&1; then
        echo "✅ Health check passed!"
        
        # 获取应用信息
        APP_INFO=$(curl -s ${HEALTH_CHECK_URL} 2>/dev/null || echo "Unable to fetch app info")
        echo "Application info: ${APP_INFO}"
        
        # 检查容器资源使用情况
        echo "Container resource usage:"
        docker stats --no-stream --format "table {{.Name}}\t{{.CPUPerc}}\t{{.MemUsage}}\t{{.NetIO}}" ${CONTAINER_NAME}
        
        echo "🎉 Deployment validation completed successfully!"
        exit 0
    else
        RETRY_COUNT=$((RETRY_COUNT + 1))
        echo "Health check failed (attempt ${RETRY_COUNT}/${MAX_RETRIES}), retrying in ${RETRY_INTERVAL} seconds..."
        sleep ${RETRY_INTERVAL}
    fi
done

echo "❌ ERROR: Health check failed after ${MAX_RETRIES} attempts"
echo "Container logs (last 50 lines):"
docker logs --tail 50 ${CONTAINER_NAME}

exit 1