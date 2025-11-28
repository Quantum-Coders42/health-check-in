#!/bin/bash

# 容器备份脚本
# 用于在部署前备份当前容器的配置和状态

CONTAINER_NAME="health-check-in"
BACKUP_DIR="/opt/backups/health-check-in"
DATE=$(date +%Y%m%d_%H%M%S)

# 创建备份目录
mkdir -p ${BACKUP_DIR}

echo "Starting backup of container: ${CONTAINER_NAME}"

# 检查容器是否存在
if docker ps -a --format "table {{.Names}}" | grep -q "^${CONTAINER_NAME}$"; then
    echo "Container found, creating backup..."
    
    # 备份容器配置
    docker inspect ${CONTAINER_NAME} > ${BACKUP_DIR}/container_config_${DATE}.json
    
    # 备份容器日志（最近1000行）
    docker logs --tail 1000 ${CONTAINER_NAME} > ${BACKUP_DIR}/container_logs_${DATE}.log 2>&1
    
    # 创建容器状态快照
    echo "Container status: $(docker inspect -f '{{.State.Status}}' ${CONTAINER_NAME})" > ${BACKUP_DIR}/status_${DATE}.txt
    echo "Container created: $(docker inspect -f '{{.Created}}' ${CONTAINER_NAME})" >> ${BACKUP_DIR}/status_${DATE}.txt
    echo "Container started: $(docker inspect -f '{{.State.StartedAt}}' ${CONTAINER_NAME})" >> ${BACKUP_DIR}/status_${DATE}.txt
    
    echo "Backup completed successfully!"
    echo "Backup files created in: ${BACKUP_DIR}"
    echo "- Container config: container_config_${DATE}.json"
    echo "- Container logs: container_logs_${DATE}.log"
    echo "- Status info: status_${DATE}.txt"
    
    # 清理30天前的备份
    find ${BACKUP_DIR} -name "*.json" -mtime +30 -delete
    find ${BACKUP_DIR} -name "*.log" -mtime +30 -delete
    find ${BACKUP_DIR} -name "*.txt" -mtime +30 -delete
    
    echo "Old backups cleaned up (older than 30 days)"
else
    echo "Container ${CONTAINER_NAME} not found, skipping backup"
fi