USE scu_edu_admin;
SET NAMES utf8mb4;

INSERT INTO sys_role (id, role_code, role_name) VALUES
(1, 'ADMIN', '系统管理员'),
(2, 'TEACHER', '教师'),
(3, 'STUDENT', '学生');

INSERT INTO sys_permission (id, permission_code, permission_name, menu_path, parent_id) VALUES
(1, 'dashboard:view', '首页', '/dashboard', NULL),
(2, 'college:manage', '学院管理', '/admin/colleges', NULL),
(3, 'major:manage', '专业管理', '/admin/majors', NULL),
(4, 'class:manage', '班级管理', '/admin/classes', NULL),
(5, 'student:manage', '学生管理', '/admin/students', NULL),
(6, 'teacher:manage', '教师管理', '/admin/teachers', NULL),
(7, 'course:manage', '课程管理', '/admin/courses', NULL),
(8, 'semester:manage', '学期管理', '/admin/semesters', NULL),
(9, 'classroom:manage', '教室管理', '/admin/classrooms', NULL),
(10, 'teaching-class:manage', '教学班管理', '/admin/teaching-classes', NULL),
(11, 'selection:manage', '选课记录管理', '/admin/selections', NULL),
(12, 'grade:manage', '成绩管理', '/admin/grades', NULL),
(13, 'user:manage', '用户管理', '/admin/users', NULL),
(14, 'statistics:view', '统计报表', '/admin/statistics', NULL),
(15, 'teacher:classes', '我的授课', '/teacher/classes', NULL),
(16, 'teacher:roster', '教学班名单', '/teacher/roster', NULL),
(17, 'teacher:grade-input', '成绩录入', '/teacher/grade-input', NULL),
(18, 'teacher:grade-statistics', '成绩统计', '/teacher/grade-statistics', NULL),
(19, 'student:course-select', '可选课程', '/student/course-select', NULL),
(20, 'student:my-course', '我的选课', '/student/my-course', NULL),
(21, 'student:schedule', '我的课表', '/student/schedule', NULL),
(22, 'student:grade', '我的成绩', '/student/grade', NULL);

INSERT INTO sys_role_permission (role_id, permission_id)
SELECT 1, id FROM sys_permission;
INSERT INTO sys_role_permission (role_id, permission_id) VALUES
(2, 1), (2, 15), (2, 16), (2, 17), (2, 18),
(3, 1), (3, 19), (3, 20), (3, 21), (3, 22);

-- BCrypt("password")
INSERT INTO sys_user (id, username, password_hash, real_name, user_type, related_id) VALUES
(1, 'admin', '$2a$10$tNLqDC68H1WvDhDvvnnx7OaqwSSfatnzuL84eFkTqxAmDuMhnmSOG', '系统管理员', 'admin', NULL),
(2, 'teacher001', '$2a$10$tNLqDC68H1WvDhDvvnnx7OaqwSSfatnzuL84eFkTqxAmDuMhnmSOG', '张明', 'teacher', 1),
(3, 'student001', '$2a$10$tNLqDC68H1WvDhDvvnnx7OaqwSSfatnzuL84eFkTqxAmDuMhnmSOG', '李华', 'student', 1);

INSERT INTO sys_user_role (user_id, role_id) VALUES
(1, 1), (2, 2), (3, 3);

INSERT INTO edu_college (id, college_code, college_name, dean_name, phone) VALUES
(1, 'SCU-CS', '计算机学院', '王教授', '028-85400001'),
(2, 'SCU-MATH', '数学学院', '陈教授', '028-85400002'),
(3, 'SCU-MED', '华西医学中心', '赵教授', '028-85400003');

INSERT INTO edu_major (id, college_id, major_code, major_name, degree_type) VALUES
(1, 1, 'CS', '计算机科学与技术', '本科'),
(2, 1, 'SE', '软件工程', '本科'),
(3, 2, 'MATH', '数学与应用数学', '本科');

INSERT INTO edu_class (id, major_id, class_code, class_name, grade_year) VALUES
(1, 1, 'CS2023-01', '计科2023级1班', 2023),
(2, 2, 'SE2023-01', '软件2023级1班', 2023),
(3, 3, 'MATH2023-01', '数学2023级1班', 2023);

INSERT INTO edu_student (id, user_id, class_id, student_no, student_name, gender, phone, email, enrollment_year, student_status) VALUES
(1, 3, 1, '202314146001', '李华', 'male', '13800000001', 'lihua@example.com', 2023, 'normal'),
(2, NULL, 1, '202314146002', '王芳', 'female', '13800000002', 'wangfang@example.com', 2023, 'normal'),
(3, NULL, 2, '202314147001', '刘洋', 'male', '13800000003', 'liuyang@example.com', 2023, 'normal'),
(4, NULL, 3, '202314148001', '赵敏', 'female', '13800000004', 'zhaomin@example.com', 2023, 'normal');

INSERT INTO edu_teacher (id, user_id, college_id, teacher_no, teacher_name, gender, title, phone, email, teacher_status) VALUES
(1, 2, 1, 'T2023001', '张明', 'male', '副教授', '13900000001', 'zhangming@example.com', 'active'),
(2, NULL, 1, 'T2023002', '周敏', 'female', '讲师', '13900000002', 'zhoumin@example.com', 'active'),
(3, NULL, 2, 'T2023003', '孙强', 'male', '教授', '13900000003', 'sunqiang@example.com', 'active');

INSERT INTO edu_semester (id, semester_code, semester_name, start_date, end_date, is_current) VALUES
(1, '2025-2026-1', '2025-2026学年第1学期', '2025-09-01', '2026-01-15', 0),
(2, '2025-2026-2', '2025-2026学年第2学期', '2026-02-23', '2026-07-03', 1);

INSERT INTO edu_course (id, college_id, course_code, course_name, credit, course_type, course_status) VALUES
(1, 1, 'DBS001', '数据库系统', 3.0, 'required', 'active'),
(2, 1, 'JAVA001', 'Java程序设计', 3.0, 'required', 'active'),
(3, 1, 'WEB001', 'Web应用开发', 2.0, 'elective', 'active'),
(4, 2, 'MATH001', '高等数学', 4.0, 'general', 'active');

INSERT INTO edu_classroom (id, building, room_no, capacity, has_multimedia, classroom_status) VALUES
(1, '望江一教', 'A101', 80, 1, 'available'),
(2, '望江一教', 'A102', 60, 1, 'available'),
(3, '江安综合楼', 'B201', 120, 1, 'available');

INSERT INTO edu_teaching_class (id, course_id, teacher_id, semester_id, teaching_class_code, capacity, selected_count, class_status) VALUES
(1, 1, 1, 2, 'DBS001-2026-01', 60, 2, 'open'),
(2, 2, 2, 2, 'JAVA001-2026-01', 50, 1, 'open'),
(3, 4, 3, 2, 'MATH001-2026-01', 100, 1, 'open'),
(4, 3, 1, 2, 'WEB001-2026-01', 45, 2, 'open');

INSERT INTO edu_teaching_class_schedule (teaching_class_id, classroom_id, weekday, start_section, end_section) VALUES
(1, 1, 1, 1, 2),
(1, 1, 3, 3, 4),
(2, 2, 2, 1, 2),
(3, 3, 4, 5, 6),
(4, 2, 5, 3, 4);

INSERT INTO edu_course_selection (id, student_id, teaching_class_id, selection_status, selected_at) VALUES
(1, 1, 1, 'selected', NOW()),
(2, 2, 1, 'selected', NOW()),
(3, 1, 2, 'selected', NOW()),
(4, 4, 3, 'selected', NOW()),
(5, 3, 4, 'selected', NOW()),
(6, 4, 4, 'selected', NOW());

INSERT INTO edu_grade (selection_id, score, grade_point, grade_status, submitted_at) VALUES
(1, 88.00, 3.70, 'submitted', NOW()),
(2, 76.00, 2.70, 'submitted', NOW()),
(3, 92.00, 4.00, 'submitted', NOW()),
(5, 84.00, 3.40, 'submitted', NOW()),
(6, 79.00, 2.90, 'submitted', NOW());

UPDATE sys_user SET related_id = 1 WHERE id = 2;
UPDATE sys_user SET related_id = 1 WHERE id = 3;
