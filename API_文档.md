# 健康打卡系统 API 文档

## 饮食记录 API

### 新增饮食打卡

**请求信息：**
- **URL:** `POST /diet/checkin`
- **请求头:** `Content-Type: application/json`, `Authorization: Bearer {token}`

**请求体：**
```json
{
  "mealType": "BREAKFAST|LUNCH|DINNER|SNACK",
  "description": "食物描述（可选）",
  "calories": 300
}
```

**响应：**
```json
{
  "code": 200,
  "message": "success",
  "data": 12345
}
```

### 获取饮食记录

**请求信息：**
- **URL:** `GET /diet/records`
- **查询参数:**
  - `startDate`: 开始日期 (yyyy-MM-dd，可选)
  - `endDate`: 结束日期 (yyyy-MM-dd，可选)
  - `mealType`: 餐食类型 (可选)

**响应：**
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 12345,
      "userId": 1,
      "recordDate": "2025-12-01",
      "mealType": "BREAKFAST",
      "description": "牛奶、面包、鸡蛋",
      "calories": 350,
      "createdAt": "2025-12-01T08:30:00"
    }
  ]
}
```

---

## 睡眠记录 API

### 新增睡眠打卡

**请求信息：**
- **URL:** `POST /sleep/checkin`
- **请求头:** `Content-Type: application/json`, `Authorization: Bearer {token}`

**请求体：**
```json
{
  "sleepTime": "2025-11-30 23:00:00",
  "wakeTime": "2025-12-01 07:30:00",
  "description": "睡眠质量很好，中途没有醒来"
}
```

**响应：**
```json
{
  "code": 200,
  "message": "success",
  "data": 12346
}
```

### 获取睡眠记录

**请求信息：**
- **URL:** `GET /sleep/records`
- **查询参数:**
  - `startDate`: 开始日期 (yyyy-MM-dd，可选)
  - `endDate`: 结束日期 (yyyy-MM-dd，可选)

**响应：**
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 12346,
      "userId": 1,
      "recordDate": "2025-12-01",
      "sleepDurationMinutes": 510,
      "sleepTime": "2025-11-30 23:00:00",
      "wakeTime": "2025-12-01 07:30:00",
      "description": "睡眠质量很好",
      "createdAt": "2025-12-01T07:35:00"
    }
  ]
}
```

---

## 饮水记录 API

### 新增饮水记录

**请求信息：**
- **URL:** `POST /water/checkin`
- **请求头:** `Content-Type: application/json`, `Authorization: Bearer {token}`

**请求体：**
```json
{
  "amountMl": 250,
  "description": "一杯温水"
}
```

**响应：**
```json
{
  "code": 200,
  "message": "success",
  "data": 12347
}
```

### 快捷饮水打卡 - 200ml

**请求信息：**
- **URL:** `POST /water/quick-checkin-200ml`
- **请求头:** `Content-Type: application/json`, `Authorization: Bearer {token}`

**请求体（可选）：**
```json
{
  "description": "早晨一杯水"
}
```

### 快捷饮水打卡 - 500ml

**请求信息：**
- **URL:** `POST /water/quick-checkin-500ml`
- **请求头:** `Content-Type: application/json`, `Authorization: Bearer {token}`

### 获取饮水记录

**请求信息：**
- **URL:** `GET /water/records`
- **查询参数:**
  - `startDate`: 开始日期 (yyyy-MM-dd，可选)
  - `endDate`: 结束日期 (yyyy-MM-dd，可选)

**响应：**
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 12347,
      "userId": 1,
      "recordDate": "2025-12-01",
      "amountMl": 250,
      "description": "一杯温水",
      "createdAt": "2025-12-01T08:00:00"
    }
  ]
}
```

### 获取每日饮水量统计

**请求信息：**
- **URL:** `GET /water/daily-summary`
- **查询参数:**
  - `date`: 查询日期 (yyyy-MM-dd，可选，默认今天)

**响应：**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "date": "2025-12-01",
    "totalIntakeMl": 1450,
    "intakeCount": 5
  }
}
```

---

## 错误代码说明

| 错误代码 | 说明 |
|---------|------|
| 10001 | 该日期已有运动打卡记录 |
| 10002 | 该日期该餐食类型已有打卡记录 |
| 10003 | 该日期已有睡眠打卡记录 |
| 10004 | 起床时间必须在入睡时间之后 |
| 40100 | 验证token失败 |

---

## 使用示例

### JavaScript/前端调用示例

```javascript
// 饮食打卡
const dietCheckIn = async () => {
  const response = await fetch('/diet/checkin', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify({
      mealType: 'BREAKFAST',
      description: '牛奶、鸡蛋、面包',
      calories: 350
    })
  });
  return await response.json();
};

// 饮水快捷打卡
const quickWaterIntake = async () => {
  const response = await fetch('/water/quick-checkin-200ml', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    }
  });
  return await response.json();
};

// 获取每日饮水量统计
const getDailyWaterSummary = async (date = null) => {
  const url = date ? `/water/daily-summary?date=${date}` : '/water/daily-summary';
  const response = await fetch(url, {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  });
  return await response.json();
};
```

### curl 命令示例

```bash
# 饮食打卡
curl -X POST http://localhost:8080/diet/checkin \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer ${token}" \
  -d '{"mealType":"LUNCH","description":"鸡肉沙拉","calories":400}'

# 饮水快捷打卡
curl -X POST http://localhost:8080/water/quick-checkin-500ml \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer ${token}"

# 获取饮水记录
curl -X GET "http://localhost:8080/water/records?startDate=2025-12-01&endDate=2025-12-07" \
  -H "Authorization: Bearer ${token}"
```