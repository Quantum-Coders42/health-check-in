# 智能部署工作流说明

## 概述

这个优化的GitHub Actions工作流实现了基于commit版本号的智能部署，具有以下特点：

- **智能构建**: 检查镜像是否已存在，避免重复构建
- **版本控制**: 使用commit SHA作为镜像标签，确保可追溯性
- **纯Docker部署**: 不依赖docker-compose，使用纯Docker命令
- **自动备份**: 部署前自动备份当前容器配置和日志
- **部署验证**: 部署后自动验证容器是否正常运行
- **回滚支持**: 提供回滚脚本，支持快速回滚到之前的版本

## 工作流程

### 1. 镜像检查阶段
- 检查目标镜像是否已在镜像仓库中存在
- 如果存在，跳过构建阶段，直接部署
- 如果不存在，执行构建和推送

### 2. 部署阶段
- 备份当前运行的容器配置和日志
- 停止并删除旧容器
- 使用指定版本的镜像启动新容器
- 验证新容器是否正常运行

### 3. 清理阶段
- 清理旧镜像（保留最近3个版本）
- 删除30天前的备份文件

## 脚本说明

### 备份脚本 (`scripts/backup-container.sh`)
- 备份容器配置到JSON文件
- 备份最近1000行日志
- 记录容器状态信息
- 自动清理30天前的备份

### 验证脚本 (`scripts/validate-deployment.sh`)
- 检查容器是否正在运行
- 验证容器状态
- 执行健康检查
- 显示容器资源使用情况

### 回滚脚本 (`scripts/rollback.sh`)
- 查找上一个稳定版本的镜像
- 交互式确认回滚操作
- 自动停止当前容器并启动回滚版本
- 验证回滚是否成功

## 环境变量配置

工作流中使用的环境变量：

```yaml
REGISTRY: crpi-9koetp78bl09eg2k.cn-shenzhen.personal.cr.aliyuncs.com
IMAGE_NAME: ${{ secrets.ALIYUN_ACR_NAMESPACE }}/health-check-in
CONTAINER_NAME: health-check-in
NETWORK_NAME: jade-network
HOST_PORT: 8080
CONTAINER_PORT: 8080
```

## 部署配置

容器启动参数：
- 端口映射: 8080:8080
- 网络: jade-network
- 重启策略: unless-stopped
- 环境变量:
  - APP_SPRING_DATASOURCE_USERNAME=root
  - APP_SPRING_DATASOURCE_PASSWORD=mysql_bzzTct
  - APP_SPRING_DATASOURCE_URL=106.55.7.149:3306
  - APP_JWT_SECRET=1c4b7624085444ad6b2d147df9db9f6e

## 使用说明

### 自动部署
当代码推送到main分支时，工作流会自动触发：
1. 检查镜像是否存在
2. 构建镜像（如果需要）
3. 部署到服务器
4. 验证部署结果

### 手动回滚
如果需要回滚到之前的版本，在服务器上执行：
```bash
cd /opt/health-check-in/scripts
./rollback.sh
```

### 查看备份
备份文件存储在：`/opt/backups/health-check-in/`

### 查看容器状态
```bash
# 查看容器状态
docker ps -f name=health-check-in

# 查看容器日志
docker logs health-check-in

# 查看容器资源使用
docker stats health-check-in
```

## 故障排查

### 部署失败
1. 检查GitHub Actions日志
2. 查看服务器上的容器日志
3. 检查网络连接和镜像拉取
4. 验证环境变量配置

### 回滚失败
1. 检查是否有可用的历史镜像
2. 验证Docker服务状态
3. 检查网络配置

### 健康检查失败
1. 检查应用端口是否正确暴露
2. 验证数据库连接配置
3. 查看应用启动日志

## 最佳实践

1. **提交信息**: 使用清晰的提交信息，便于版本追踪
2. **测试**: 在推送代码前进行充分测试
3. **监控**: 部署后监控应用性能和日志
4. **备份**: 定期检查和清理备份文件
5. **回滚**: 保留足够的历史版本以便回滚