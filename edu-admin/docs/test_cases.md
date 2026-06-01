# 测试用例

## 1. 基本表 CRUD

| 用例 | 步骤 | 预期 |
| --- | --- | --- |
| 新增学院 | POST `/api/colleges` | 返回新增记录 |
| 查询学院 | GET `/api/colleges?pageNum=1&pageSize=10` | 返回分页记录 |
| 修改课程 | PUT `/api/courses/{id}` | 课程名称或状态更新 |
| 删除教室 | DELETE `/api/classrooms/{id}` | 返回 success |

## 2. 多表连接查询

| 用例 | 步骤 | 预期 |
| --- | --- | --- |
| 学生课表 | GET `/api/reports/student-schedule?studentId=1` | 返回课程、教师、教室、节次 |
| 教师名单 | GET `/api/reports/teacher-roster?teacherId=1` | 返回教学班学生名单 |
| 课程统计 | GET `/api/reports/grade-statistics?courseId=1` | 返回平均分、最高分、最低分、及格率 |

## 3. 完整性约束

| 用例 | 步骤 | 预期 |
| --- | --- | --- |
| 重复学号 | 插入相同 `student_no` | 数据库拒绝 |
| 非法成绩 | 插入 `score=120` | 检查约束拒绝 |
| 无效状态 | 插入 `course_status='x'` | 检查约束拒绝 |

## 4. 选课事务

| 用例 | 步骤 | 预期 |
| --- | --- | --- |
| 正常选课 | POST `/api/course-selections/select` | 选课成功，教学班已选人数 +1 |
| 重复选课 | 再次选同一教学班 | 返回业务错误 |
| 容量满 | 教学班已满后选课 | 返回容量已满 |
| 时间冲突 | 选择节次重叠课程 | 返回时间冲突 |

## 5. 成绩录入

| 用例 | 步骤 | 预期 |
| --- | --- | --- |
| 批量录入 | POST `/api/grades/batch` | 插入或更新成绩 |
| 自动绩点 | 录入 92 分 | 绩点为 4.00 |

## 6. 登录权限

| 用例 | 步骤 | 预期 |
| --- | --- | --- |
| 管理员登录 | admin/password | 返回全部菜单 |
| 教师登录 | teacher001/password | 返回教师菜单 |
| 学生登录 | student001/password | 返回学生菜单 |
