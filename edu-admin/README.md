# SCU EduAdmin

高校教务管理系统课程设计项目，采用前后端分离架构：

- `backend`: Spring Boot + MyBatis Plus + MySQL
- `frontend`: Vue 3 + Vite + Element Plus
- `docs`: 数据库设计、接口设计、测试用例和 PlantUML 图

## 第一版目录

```text
edu-admin/
  backend/
    pom.xml
    src/main/java/com/scu/eduadmin/
    src/main/resources/
      application.yml
      mapper/
      sql/
        schema.sql
        data.sql
  frontend/
    package.json
    vite.config.js
    index.html
    src/
  docs/
    database_design.md
    api_design.md
    test_cases.md
    course_report_material.md
    diagrams/
```

## 运行顺序

1. 在 MySQL 8.x 中执行 `backend/src/main/resources/sql/schema.sql`
2. 继续执行 `backend/src/main/resources/sql/data.sql`
3. 启动后端：`cd backend && mvn spring-boot:run`
4. 启动前端：`cd frontend && npm install && npm run dev`

演示账号默认密码均为 `password`：

- 管理员：`admin`
- 教师：`teacher001`
- 学生：`student001`
