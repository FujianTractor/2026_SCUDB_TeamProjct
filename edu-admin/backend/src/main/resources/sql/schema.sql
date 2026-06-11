DROP DATABASE IF EXISTS scu_edu_admin;
CREATE DATABASE scu_edu_admin DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE scu_edu_admin;
SET NAMES utf8mb4;

SET FOREIGN_KEY_CHECKS = 0;

DROP VIEW IF EXISTS v_student_course_schedule;
DROP VIEW IF EXISTS v_teacher_course_roster;
DROP VIEW IF EXISTS v_course_grade_statistics;
DROP VIEW IF EXISTS v_teaching_class_detail;
DROP VIEW IF EXISTS v_student_course_selection_detail;
DROP VIEW IF EXISTS v_student_grade_detail;

DROP TABLE IF EXISTS edu_operation_log;
DROP TABLE IF EXISTS edu_grade;
DROP TABLE IF EXISTS edu_course_selection;
DROP TABLE IF EXISTS edu_teaching_class_schedule;
DROP TABLE IF EXISTS edu_teaching_class;
DROP TABLE IF EXISTS edu_classroom;
DROP TABLE IF EXISTS edu_course;
DROP TABLE IF EXISTS edu_semester;
DROP TABLE IF EXISTS edu_teacher;
DROP TABLE IF EXISTS edu_student;
DROP TABLE IF EXISTS edu_class;
DROP TABLE IF EXISTS edu_major;
DROP TABLE IF EXISTS edu_college;
DROP TABLE IF EXISTS sys_role_permission;
DROP TABLE IF EXISTS sys_user_role;
DROP TABLE IF EXISTS sys_permission;
DROP TABLE IF EXISTS sys_role;
DROP TABLE IF EXISTS sys_user;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(64) NOT NULL,
  password_hash VARCHAR(128) NOT NULL,
  real_name VARCHAR(64) NOT NULL,
  user_type VARCHAR(20) NOT NULL,
  related_id BIGINT NULL,
  user_status VARCHAR(20) NOT NULL DEFAULT 'active',
  last_login_at DATETIME NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT uk_sys_user_username UNIQUE (username),
  CONSTRAINT ck_sys_user_type CHECK (user_type IN ('admin', 'teacher', 'student')),
  CONSTRAINT ck_sys_user_status CHECK (user_status IN ('active', 'disabled'))
) ENGINE=InnoDB;

CREATE TABLE sys_role (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  role_code VARCHAR(64) NOT NULL,
  role_name VARCHAR(64) NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT uk_sys_role_code UNIQUE (role_code)
) ENGINE=InnoDB;

CREATE TABLE sys_permission (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  permission_code VARCHAR(100) NOT NULL,
  permission_name VARCHAR(100) NOT NULL,
  menu_path VARCHAR(150) NULL,
  parent_id BIGINT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT uk_sys_permission_code UNIQUE (permission_code),
  CONSTRAINT fk_permission_parent FOREIGN KEY (parent_id) REFERENCES sys_permission(id)
) ENGINE=InnoDB;

CREATE TABLE sys_user_role (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT uk_user_role UNIQUE (user_id, role_id),
  CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES sys_user(id),
  CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES sys_role(id)
) ENGINE=InnoDB;

CREATE TABLE sys_role_permission (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  role_id BIGINT NOT NULL,
  permission_id BIGINT NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT uk_role_permission UNIQUE (role_id, permission_id),
  CONSTRAINT fk_role_permission_role FOREIGN KEY (role_id) REFERENCES sys_role(id),
  CONSTRAINT fk_role_permission_permission FOREIGN KEY (permission_id) REFERENCES sys_permission(id)
) ENGINE=InnoDB;

CREATE TABLE edu_college (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  college_code VARCHAR(32) NOT NULL,
  college_name VARCHAR(100) NOT NULL,
  dean_name VARCHAR(64) NULL,
  phone VARCHAR(32) NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT uk_college_code UNIQUE (college_code),
  CONSTRAINT uk_college_name UNIQUE (college_name)
) ENGINE=InnoDB;

CREATE TABLE edu_major (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  college_id BIGINT NOT NULL,
  major_code VARCHAR(32) NOT NULL,
  major_name VARCHAR(100) NOT NULL,
  degree_type VARCHAR(32) NOT NULL DEFAULT '本科',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT uk_major_code UNIQUE (major_code),
  CONSTRAINT uk_major_college_name UNIQUE (college_id, major_name),
  CONSTRAINT fk_major_college FOREIGN KEY (college_id) REFERENCES edu_college(id)
) ENGINE=InnoDB;

CREATE TABLE edu_class (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  major_id BIGINT NOT NULL,
  class_code VARCHAR(32) NOT NULL,
  class_name VARCHAR(100) NOT NULL,
  grade_year INT NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT uk_class_code UNIQUE (class_code),
  CONSTRAINT fk_class_major FOREIGN KEY (major_id) REFERENCES edu_major(id),
  CONSTRAINT ck_class_grade CHECK (grade_year BETWEEN 2000 AND 2100)
) ENGINE=InnoDB;

CREATE TABLE edu_student (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NULL,
  class_id BIGINT NOT NULL,
  student_no VARCHAR(32) NOT NULL,
  student_name VARCHAR(64) NOT NULL,
  gender VARCHAR(10) NOT NULL,
  phone VARCHAR(32) NULL,
  email VARCHAR(100) NULL,
  enrollment_year INT NOT NULL,
  student_status VARCHAR(20) NOT NULL DEFAULT 'normal',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT uk_student_no UNIQUE (student_no),
  CONSTRAINT fk_student_user FOREIGN KEY (user_id) REFERENCES sys_user(id),
  CONSTRAINT fk_student_class FOREIGN KEY (class_id) REFERENCES edu_class(id),
  CONSTRAINT ck_student_gender CHECK (gender IN ('male', 'female')),
  CONSTRAINT ck_student_status CHECK (student_status IN ('normal', 'suspended', 'graduated'))
) ENGINE=InnoDB;

CREATE TABLE edu_teacher (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NULL,
  college_id BIGINT NOT NULL,
  teacher_no VARCHAR(32) NOT NULL,
  teacher_name VARCHAR(64) NOT NULL,
  gender VARCHAR(10) NOT NULL,
  title VARCHAR(32) NOT NULL,
  phone VARCHAR(32) NULL,
  email VARCHAR(100) NULL,
  teacher_status VARCHAR(20) NOT NULL DEFAULT 'active',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT uk_teacher_no UNIQUE (teacher_no),
  CONSTRAINT fk_teacher_user FOREIGN KEY (user_id) REFERENCES sys_user(id),
  CONSTRAINT fk_teacher_college FOREIGN KEY (college_id) REFERENCES edu_college(id),
  CONSTRAINT ck_teacher_gender CHECK (gender IN ('male', 'female')),
  CONSTRAINT ck_teacher_status CHECK (teacher_status IN ('active', 'retired'))
) ENGINE=InnoDB;

CREATE TABLE edu_semester (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  semester_code VARCHAR(32) NOT NULL,
  semester_name VARCHAR(64) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  is_current TINYINT NOT NULL DEFAULT 0,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT uk_semester_code UNIQUE (semester_code),
  CONSTRAINT ck_semester_date CHECK (start_date < end_date),
  CONSTRAINT ck_semester_current CHECK (is_current IN (0, 1))
) ENGINE=InnoDB;

CREATE TABLE edu_course (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  college_id BIGINT NOT NULL,
  course_code VARCHAR(32) NOT NULL,
  course_name VARCHAR(100) NOT NULL,
  credit DECIMAL(3,1) NOT NULL,
  course_type VARCHAR(20) NOT NULL,
  course_status VARCHAR(20) NOT NULL DEFAULT 'active',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT uk_course_code UNIQUE (course_code),
  CONSTRAINT fk_course_college FOREIGN KEY (college_id) REFERENCES edu_college(id),
  CONSTRAINT ck_course_credit CHECK (credit > 0),
  CONSTRAINT ck_course_type CHECK (course_type IN ('required', 'elective', 'general')),
  CONSTRAINT ck_course_status CHECK (course_status IN ('active', 'inactive'))
) ENGINE=InnoDB;

CREATE TABLE edu_classroom (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  building VARCHAR(64) NOT NULL,
  room_no VARCHAR(32) NOT NULL,
  capacity INT NOT NULL,
  has_multimedia TINYINT NOT NULL DEFAULT 1,
  classroom_status VARCHAR(20) NOT NULL DEFAULT 'available',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT uk_classroom_room UNIQUE (building, room_no),
  CONSTRAINT ck_classroom_capacity CHECK (capacity > 0),
  CONSTRAINT ck_classroom_multimedia CHECK (has_multimedia IN (0, 1)),
  CONSTRAINT ck_classroom_status CHECK (classroom_status IN ('available', 'maintenance'))
) ENGINE=InnoDB;

CREATE TABLE edu_teaching_class (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  course_id BIGINT NOT NULL,
  teacher_id BIGINT NOT NULL,
  semester_id BIGINT NOT NULL,
  teaching_class_code VARCHAR(32) NOT NULL,
  capacity INT NOT NULL,
  selected_count INT NOT NULL DEFAULT 0,
  class_status VARCHAR(20) NOT NULL DEFAULT 'open',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT uk_teaching_class_code UNIQUE (teaching_class_code),
  CONSTRAINT fk_teaching_class_course FOREIGN KEY (course_id) REFERENCES edu_course(id),
  CONSTRAINT fk_teaching_class_teacher FOREIGN KEY (teacher_id) REFERENCES edu_teacher(id),
  CONSTRAINT fk_teaching_class_semester FOREIGN KEY (semester_id) REFERENCES edu_semester(id),
  CONSTRAINT ck_teaching_class_capacity CHECK (capacity > 0),
  CONSTRAINT ck_teaching_class_count CHECK (selected_count >= 0 AND selected_count <= capacity),
  CONSTRAINT ck_teaching_class_status CHECK (class_status IN ('open', 'closed'))
) ENGINE=InnoDB;

CREATE TABLE edu_teaching_class_schedule (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  teaching_class_id BIGINT NOT NULL,
  classroom_id BIGINT NOT NULL,
  weekday INT NOT NULL,
  start_section INT NOT NULL,
  end_section INT NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_schedule_teaching_class FOREIGN KEY (teaching_class_id) REFERENCES edu_teaching_class(id),
  CONSTRAINT fk_schedule_classroom FOREIGN KEY (classroom_id) REFERENCES edu_classroom(id),
  CONSTRAINT uk_schedule_class_time UNIQUE (classroom_id, weekday, start_section, end_section),
  CONSTRAINT ck_schedule_weekday CHECK (weekday BETWEEN 1 AND 7),
  CONSTRAINT ck_schedule_section CHECK (start_section BETWEEN 1 AND 12 AND end_section BETWEEN start_section AND 12)
) ENGINE=InnoDB;

CREATE TABLE edu_course_selection (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  student_id BIGINT NOT NULL,
  teaching_class_id BIGINT NOT NULL,
  selection_status VARCHAR(20) NOT NULL DEFAULT 'selected',
  selected_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  dropped_at DATETIME NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT uk_selection_student_class UNIQUE (student_id, teaching_class_id),
  CONSTRAINT fk_selection_student FOREIGN KEY (student_id) REFERENCES edu_student(id),
  CONSTRAINT fk_selection_teaching_class FOREIGN KEY (teaching_class_id) REFERENCES edu_teaching_class(id),
  CONSTRAINT ck_selection_status CHECK (selection_status IN ('selected', 'dropped'))
) ENGINE=InnoDB;

CREATE TABLE edu_grade (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  selection_id BIGINT NOT NULL,
  score DECIMAL(5,2) NULL,
  grade_point DECIMAL(3,2) NULL,
  grade_status VARCHAR(20) NOT NULL DEFAULT 'unsubmitted',
  submitted_at DATETIME NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT uk_grade_selection UNIQUE (selection_id),
  CONSTRAINT fk_grade_selection FOREIGN KEY (selection_id) REFERENCES edu_course_selection(id),
  CONSTRAINT ck_grade_score CHECK (score IS NULL OR (score >= 0 AND score <= 100)),
  CONSTRAINT ck_grade_point CHECK (grade_point IS NULL OR (grade_point >= 0 AND grade_point <= 4)),
  CONSTRAINT ck_grade_status CHECK (grade_status IN ('unsubmitted', 'submitted'))
) ENGINE=InnoDB;

CREATE TABLE edu_operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NULL,
  module_name VARCHAR(64) NOT NULL,
  operation_type VARCHAR(32) NOT NULL,
  operation_content VARCHAR(500) NOT NULL,
  ip_address VARCHAR(64) NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_log_user FOREIGN KEY (user_id) REFERENCES sys_user(id)
) ENGINE=InnoDB;

CREATE INDEX idx_major_college ON edu_major(college_id);
CREATE INDEX idx_class_major ON edu_class(major_id);
CREATE INDEX idx_student_class ON edu_student(class_id);
CREATE INDEX idx_teacher_college ON edu_teacher(college_id);
CREATE INDEX idx_teaching_class_course ON edu_teaching_class(course_id);
CREATE INDEX idx_teaching_class_teacher ON edu_teaching_class(teacher_id);
CREATE INDEX idx_selection_student ON edu_course_selection(student_id);
CREATE INDEX idx_grade_status ON edu_grade(grade_status);

CREATE VIEW v_student_course_schedule AS
SELECT
  s.id AS student_id,
  s.student_no,
  s.student_name,
  sem.id AS semester_id,
  sem.semester_name,
  c.course_code,
  c.course_name,
  c.credit,
  t.teacher_name,
  tc.teaching_class_code,
  sch.weekday,
  sch.start_section,
  sch.end_section,
  cr.building,
  cr.room_no
FROM edu_course_selection cs
JOIN edu_student s ON cs.student_id = s.id
JOIN edu_teaching_class tc ON cs.teaching_class_id = tc.id
JOIN edu_course c ON tc.course_id = c.id
JOIN edu_teacher t ON tc.teacher_id = t.id
JOIN edu_semester sem ON tc.semester_id = sem.id
JOIN edu_teaching_class_schedule sch ON sch.teaching_class_id = tc.id
JOIN edu_classroom cr ON sch.classroom_id = cr.id
WHERE cs.selection_status = 'selected';

CREATE VIEW v_teacher_course_roster AS
SELECT
  t.id AS teacher_id,
  t.teacher_no,
  t.teacher_name,
  tc.id AS teaching_class_id,
  tc.teaching_class_code,
  c.course_name,
  cs.id AS selection_id,
  s.student_no,
  s.student_name,
  cls.class_name,
  cs.selection_status
FROM edu_teacher t
JOIN edu_teaching_class tc ON tc.teacher_id = t.id
JOIN edu_course c ON tc.course_id = c.id
JOIN edu_course_selection cs ON cs.teaching_class_id = tc.id
JOIN edu_student s ON cs.student_id = s.id
JOIN edu_class cls ON s.class_id = cls.id;

CREATE VIEW v_course_grade_statistics AS
SELECT
  c.id AS course_id,
  c.course_code,
  c.course_name,
  tc.id AS teaching_class_id,
  tc.teaching_class_code,
  COUNT(g.id) AS submitted_count,
  ROUND(AVG(g.score), 2) AS avg_score,
  MAX(g.score) AS max_score,
  MIN(g.score) AS min_score,
  ROUND(SUM(CASE WHEN g.score >= 60 THEN 1 ELSE 0 END) / NULLIF(COUNT(g.id), 0) * 100, 2) AS pass_rate
FROM edu_course c
JOIN edu_teaching_class tc ON tc.course_id = c.id
LEFT JOIN edu_course_selection cs ON cs.teaching_class_id = tc.id AND cs.selection_status = 'selected'
LEFT JOIN edu_grade g ON g.selection_id = cs.id AND g.grade_status = 'submitted'
GROUP BY c.id, c.course_code, c.course_name, tc.id, tc.teaching_class_code;

CREATE VIEW v_teaching_class_detail AS
SELECT
  tc.id,
  tc.course_id,
  tc.teacher_id,
  tc.semester_id,
  tc.teaching_class_code,
  tc.capacity,
  tc.selected_count,
  tc.class_status,
  c.course_code,
  c.course_name,
  t.teacher_name,
  sem.semester_name,
  sem.is_current
FROM edu_teaching_class tc
JOIN edu_course c ON c.id = tc.course_id
JOIN edu_teacher t ON t.id = tc.teacher_id
JOIN edu_semester sem ON sem.id = tc.semester_id;

CREATE VIEW v_student_course_selection_detail AS
SELECT
  cs.id AS selection_id,
  cs.student_id,
  cs.teaching_class_id,
  cs.selection_status,
  cs.selected_at,
  c.course_code,
  c.course_name,
  c.credit,
  t.teacher_name,
  sem.semester_name,
  tc.teaching_class_code
FROM edu_course_selection cs
JOIN edu_teaching_class tc ON tc.id = cs.teaching_class_id
JOIN edu_course c ON c.id = tc.course_id
JOIN edu_teacher t ON t.id = tc.teacher_id
JOIN edu_semester sem ON sem.id = tc.semester_id;

CREATE VIEW v_student_grade_detail AS
SELECT
  cs.id AS selection_id,
  cs.student_id,
  c.course_name,
  c.credit,
  sem.semester_name,
  sem.start_date,
  g.score
FROM edu_grade g
JOIN edu_course_selection cs ON g.selection_id = cs.id
JOIN edu_teaching_class tc ON cs.teaching_class_id = tc.id
JOIN edu_course c ON tc.course_id = c.id
JOIN edu_semester sem ON sem.id = tc.semester_id;

-- 复杂查询 1：学生按学期查询个人课表
-- SELECT * FROM v_student_course_schedule WHERE student_id = ? AND semester_id = ? ORDER BY weekday, start_section;

-- 复杂查询 2：教师查询某教学班学生名单
-- SELECT * FROM v_teacher_course_roster WHERE teacher_id = ? AND teaching_class_id = ? ORDER BY student_no;

-- 复杂查询 3：管理员按学院、专业、班级查询学生
-- SELECT s.* FROM edu_student s
-- JOIN edu_class c ON s.class_id = c.id
-- JOIN edu_major m ON c.major_id = m.id
-- JOIN edu_college co ON m.college_id = co.id
-- WHERE co.id = ? AND m.id = ? AND c.id = ?;

-- 复杂查询 4：统计某课程平均分、最高分、最低分、及格率
-- SELECT * FROM v_course_grade_statistics WHERE course_id = ?;

-- 复杂查询 5：查询学生已修学分和平均绩点
-- SELECT s.id, s.student_no, s.student_name, SUM(c.credit) AS completed_credit, ROUND(AVG(g.grade_point), 2) AS avg_grade_point
-- FROM edu_student s
-- JOIN edu_course_selection cs ON cs.student_id = s.id AND cs.selection_status = 'selected'
-- JOIN edu_grade g ON g.selection_id = cs.id AND g.grade_status = 'submitted' AND g.score >= 60
-- JOIN edu_teaching_class tc ON tc.id = cs.teaching_class_id
-- JOIN edu_course c ON c.id = tc.course_id
-- WHERE s.id = ?
-- GROUP BY s.id, s.student_no, s.student_name;
