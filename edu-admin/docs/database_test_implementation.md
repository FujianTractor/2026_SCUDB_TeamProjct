# 数据库测试实现说明

项目在 `backend/src/test/java/com/scu/eduadmin/DatabaseRequirementTest.java` 中提供三类可执行测试。

## 1. 基本表 CRUD

以学院表 `edu_college` 为对象，在同一事务中完成新增、按主键查询、修改和删除，并逐步断言数据库结果。测试结束后事务自动回滚，不保留测试数据。

## 2. 多表连接与多条件查询

调用 `ReportMapper.studentSchedule(studentId, semesterId)`，查询视图 `v_student_course_schedule`。该视图连接选课、学生、教学班、课程、教师、学期、排课和教室等表；测试同时使用学生和学期两个条件，并验证课程、教师和教室字段均被正确返回。

## 3. 完整性约束

分别验证三类约束：

- 唯一约束：重复插入相同学号时数据库拒绝。
- 检查约束：插入 `score=120` 的成绩时数据库拒绝。
- 外键约束：删除仍被专业、教师或课程引用的学院时数据库拒绝。

外键异常由 `DataIntegrityMessageResolver` 转换为面向用户的中文提示，其映射测试位于 `DataIntegrityMessageResolverTest.java`。

## 运行方法

先完成 `schema.sql` 和 `data.sql` 初始化，并确保 `application.yml` 中的 MySQL 配置有效，然后执行：

```powershell
cd backend
mvn -DrunDatabaseTests=true -Dtest=DatabaseRequirementTest test
```

普通的 `mvn test` 不会连接数据库，只执行不依赖外部环境的单元测试。
