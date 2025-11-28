#!/bin/bash

# 回滚脚本
# 用于在部署失败时回滚到之前的版本

CONTAINER_NAME="health-check-in"
NETWORK_NAME="jade-network"
HOST_PORT="8080"
CONTAINER_PORT="8080"
REGISTRY="crpi-9koetp78bl09eg2k.cn-shenzhen.personal.cr.aliyuncs.com"
IMAGE_NAME="mocheen/health-check-in"

# 获取上一个稳定版本的镜像
echo "Searching for previous stable image..."
PREVIOUS_IMAGE=$(docker images "${REGISTRY}/${IMAGE_NAME}" --format "table {{.Repository}}:{{.Tag}}" | grep -v "latest" | head -n 2 | tail -n 1)

if [ -z "${PREVIOUS_IMAGE}" ]; then
    echo "❌ ERROR: No previous image found for rollback!"
    echo "Available images:"
    docker images "${REGISTRY}/${IMAGE_NAME}" --format "table {{.Repository}}:{{.Tag}}\t{{.CreatedAt}}"
    exit 1
fi

echo "Found previous image: ${PREVIOUS_IMAGE}"

# 确认回滚
echo "⚠️  WARNING: This will rollback to the previous version!"
echo "Container will be stopped and replaced with: ${PREVIOUS_IMAGE}"
read -p "Are you sure you want to continue? (y/N): " -n 1 -r
echo
if [[ ! $REPLY =~ ^[Yy]$ ]]; then
    echo "Rollback cancelled."
    exit 0
fi

# 停止当前容器
echo "Stopping current container..."
docker stop ${CONTAINER_NAME} 2>/dev/null || true
docker rm ${CONTAINER_NAME} 2>/dev/null || true

# 启动回滚容器
echo "Starting rollback container..."
docker run -d \
  --name ${CONTAINER_NAME} \
  --network ${NETWORK_NAME} \
  --restart unless-stopped \
  -p ${HOST_PORT}:${CONTAINER_PORT} \
  -e APP_SPRING_DATASOURCE_USERNAME=root \
  -e APP_SPRING_DATASOURCE_PASSWORD=mysql_bzzTct \
  -e APP_SPRING_DATASOURCE_URL=106.55.7.149:3306 \
  -e APP_JWT_SECRET=1c4b7624085444ad6b2d147df9db9f6e \
  ${PREVIOUS_IMAGE}

# 等待容器启动
echo "Waiting for container to start..."
sleep 15

# 验证回滚
echo "Validating rollback..."
if docker ps --format "table {{.Names}}" | grep -q "^${CONTAINER_NAME}$"; then
    CONTAINER_STATUS=$(docker inspect -f '{{.State.Status}}' ${CONTAINER_NAME})
    if [ "${CONTAINER_STATUS}" = "running" ]; then
        echo "✅ Rollback completed successfully!"
        echo "Container is now running: ${PREVIOUS_IMAGE}"
        
        # 显示容器信息
        echo "Container info:"
        docker ps -f name=${CONTAINER_NAME} --format "table {{.Names}}\t{{.Image}}\t{{.Status}}"
        
        exit 0
    fi
fi

echo "❌ ERROR: Rollback failed!"
echo "Container logs (last 50 lines):"
docker logs --tail 50 ${CONTAINER_NAME}
exit 1