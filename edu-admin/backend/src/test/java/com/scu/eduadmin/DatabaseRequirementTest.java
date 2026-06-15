package com.scu.eduadmin;

import com.scu.eduadmin.mapper.ReportMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@EnabledIfSystemProperty(named = "runDatabaseTests", matches = "true")
class DatabaseRequirementTest {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private ReportMapper reportMapper;

  @Test
  void basicTableCrudWorks() {
    String suffix = Long.toString(System.nanoTime());
    String collegeCode = "TEST-" + suffix;
    String collegeName = "测试学院-" + suffix;

    jdbcTemplate.update("""
        INSERT INTO edu_college (college_code, college_name, dean_name, phone)
        VALUES (?, ?, ?, ?)
        """, collegeCode, collegeName, "测试负责人", "028-00000000");

    Long collegeId = jdbcTemplate.queryForObject(
        "SELECT id FROM edu_college WHERE college_code = ?",
        Long.class,
        collegeCode
    );
    assertTrue(collegeId != null && collegeId > 0);

    assertEquals(
        collegeName,
        jdbcTemplate.queryForObject(
            "SELECT college_name FROM edu_college WHERE id = ?",
            String.class,
            collegeId
        )
    );

    jdbcTemplate.update(
        "UPDATE edu_college SET dean_name = ? WHERE id = ?",
        "修改后负责人",
        collegeId
    );
    assertEquals(
        "修改后负责人",
        jdbcTemplate.queryForObject(
            "SELECT dean_name FROM edu_college WHERE id = ?",
            String.class,
            collegeId
        )
    );

    assertEquals(1, jdbcTemplate.update("DELETE FROM edu_college WHERE id = ?", collegeId));
    assertEquals(
        0,
        jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM edu_college WHERE id = ?",
            Integer.class,
            collegeId
        )
    );
  }

  @Test
  void multiTableViewSupportsMultipleConditions() {
    var rows = reportMapper.studentSchedule(1L, 2L);

    assertFalse(rows.isEmpty());
    assertTrue(rows.stream().allMatch(row -> numberValue(row, "student_id") == 1L));
    assertTrue(rows.stream().allMatch(row -> numberValue(row, "semester_id") == 2L));
    assertTrue(rows.stream().allMatch(row ->
        containsKeyIgnoreCase(row, "course_name")
            && containsKeyIgnoreCase(row, "teacher_name")
            && containsKeyIgnoreCase(row, "building")
    ));
  }

  @Test
  void uniqueConstraintRejectsDuplicateStudentNumber() {
    assertThrows(DataIntegrityViolationException.class, () -> jdbcTemplate.update("""
        INSERT INTO edu_student
          (user_id, class_id, student_no, student_name, gender, enrollment_year, student_status)
        VALUES
          (NULL, 1, '202314146001', '重复学号测试', 'male', 2023, 'normal')
        """));
  }

  @Test
  void checkConstraintRejectsInvalidScore() {
    Long selectionId = jdbcTemplate.queryForObject("""
        SELECT cs.id
        FROM edu_course_selection cs
        LEFT JOIN edu_grade g ON g.selection_id = cs.id
        WHERE g.id IS NULL
        LIMIT 1
        """, Long.class);

    assertThrows(DataAccessException.class, () -> jdbcTemplate.update("""
        INSERT INTO edu_grade (selection_id, score, grade_status)
        VALUES (?, 120, 'submitted')
        """, selectionId));
  }

  @Test
  void foreignKeyConstraintRejectsDeletingReferencedCollege() {
    Long collegeId = jdbcTemplate.queryForObject("""
        SELECT c.id
        FROM edu_college c
        WHERE EXISTS (SELECT 1 FROM edu_major m WHERE m.college_id = c.id)
           OR EXISTS (SELECT 1 FROM edu_teacher t WHERE t.college_id = c.id)
           OR EXISTS (SELECT 1 FROM edu_course co WHERE co.college_id = c.id)
        LIMIT 1
        """, Long.class);

    assertThrows(
        DataIntegrityViolationException.class,
        () -> jdbcTemplate.update("DELETE FROM edu_college WHERE id = ?", collegeId)
    );
  }

  private static long numberValue(Map<String, Object> row, String key) {
    return row.entrySet().stream()
        .filter(entry -> entry.getKey().equalsIgnoreCase(key))
        .map(Map.Entry::getValue)
        .filter(Number.class::isInstance)
        .map(Number.class::cast)
        .mapToLong(Number::longValue)
        .findFirst()
        .orElseThrow();
  }

  private static boolean containsKeyIgnoreCase(Map<String, Object> row, String key) {
    return row.keySet().stream().anyMatch(candidate -> candidate.equalsIgnoreCase(key));
  }
}
