# 数据库设计

## 1. 系统数据需求

系统围绕高校教务管理展开，核心数据包括用户、角色、权限、学院、专业、班级、学生、教师、课程、学期、教室、教学班、教学班课表、选课记录、成绩和操作日志。

## 2. ER 图说明

学院与专业、教师是一对多；专业与班级是一对多；班级与学生是一对多；课程、教师、学期共同形成教学班；教学班与学生通过选课表形成多对多；选课记录与成绩是一对零一或一对一。

## 3. 关系模式

- sys_user(id, username, password_hash, real_name, user_type, related_id, user_status)
- sys_role(id, role_code, role_name)
- sys_permission(id, permission_code, permission_name, menu_path, parent_id)
- edu_college(id, college_code, college_name, dean_name, phone)
- edu_major(id, college_id, major_code, major_name, degree_type)
- edu_class(id, major_id, class_code, class_name, grade_year)
- edu_student(id, user_id, class_id, student_no, student_name, gender, enrollment_year, student_status)
- edu_teacher(id, user_id, college_id, teacher_no, teacher_name, gender, title, teacher_status)
- edu_course(id, college_id, course_code, course_name, credit, course_type, course_status)
- edu_teaching_class(id, course_id, teacher_id, semester_id, teaching_class_code, capacity, selected_count, class_status)
- edu_course_selection(id, student_id, teaching_class_id, selection_status)
- edu_grade(id, selection_id, score, grade_point, grade_status)

## 4. 主键、外键说明

每张表均使用 `id` 作为主键。外键覆盖学院-专业、专业-班级、班级-学生、学院-教师、课程-教学班、教师-教学班、学期-教学班、教学班-课表、学生-选课、教学班-选课、选课-成绩、用户-角色、角色-权限等关系。

## 5. 完整性约束说明

数据库通过主键、外键、唯一约束、非空约束和检查约束保证数据质量。典型唯一约束包括 `student_no`、`teacher_no`、`course_code`、`building + room_no`。状态字段使用 `CHECK` 限定取值范围。

## 6. 范式分析

主要业务表满足 3NF：字段原子化；非主属性完全依赖主键；学院名、专业名、班级名等不在学生表冗余存储，而通过外键关联，避免传递依赖。

## 7. 视图设计

- `v_student_course_schedule`: 学生课表视图。
- `v_teacher_course_roster`: 教师授课名单视图。
- `v_course_grade_statistics`: 课程成绩统计视图。

## 8. 复杂查询设计

复杂查询已在 `schema.sql` 底部给出 SQL 示例，并在 `ReportMapper.xml` 中实现，包括学生课表、教师名单、课程成绩统计、已修学分和 Dashboard 指标。

## 9. 事务设计

选课、退课、成绩批量录入、删除教学班检查均在 Service 层用 `@Transactional` 实现。选课事务检查容量、重复选课、课程状态和时间冲突，成功后插入选课记录并增加已选人数。
