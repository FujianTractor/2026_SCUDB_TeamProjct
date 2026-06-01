# API 设计

统一返回格式：

```json
{ "code": 200, "message": "success", "data": {} }
```

## 登录

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| POST | `/api/auth/login` | 用户名密码登录 |

请求：

```json
{ "username": "admin", "password": "password" }
```

## 基础 CRUD

| 模块 | 路径 |
| --- | --- |
| 学院 | `/api/colleges` |
| 专业 | `/api/majors` |
| 班级 | `/api/classes` |
| 学生 | `/api/students` |
| 教师 | `/api/teachers` |
| 课程 | `/api/courses` |
| 学期 | `/api/semesters` |
| 教室 | `/api/classrooms` |
| 教学班 | `/api/teaching-classes` |
| 用户 | `/api/users` |

每个模块支持：

- `GET /api/{module}?pageNum=1&pageSize=10&keyword=数据库`
- `POST /api/{module}`
- `PUT /api/{module}/{id}`
- `DELETE /api/{module}/{id}`

## 选课

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| POST | `/api/course-selections/select` | 学生选课 |
| POST | `/api/course-selections/drop` | 学生退课 |
| GET | `/api/course-selections` | 查询选课记录 |

请求：

```json
{ "studentId": 1, "teachingClassId": 1 }
```

## 成绩

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/grades` | 查询成绩 |
| POST | `/api/grades/batch` | 批量录入成绩 |

请求：

```json
{
  "grades": [
    { "selectionId": 1, "score": 88 },
    { "selectionId": 2, "score": 76 }
  ]
}
```

## 报表

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/reports/dashboard` | 首页指标 |
| GET | `/api/reports/student-schedule?studentId=1&semesterId=2` | 学生课表 |
| GET | `/api/reports/teacher-roster?teacherId=1&teachingClassId=1` | 教师名单 |
| GET | `/api/reports/grade-statistics?courseId=1` | 成绩统计 |
| GET | `/api/reports/completed-credits?studentId=1` | 已修学分 |
