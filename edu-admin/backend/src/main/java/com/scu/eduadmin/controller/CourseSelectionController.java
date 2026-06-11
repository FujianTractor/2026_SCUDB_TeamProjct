package com.scu.eduadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.eduadmin.common.ApiResponse;
import com.scu.eduadmin.dto.CourseSelectRequest;
import com.scu.eduadmin.entity.EduCourseSelection;
import com.scu.eduadmin.service.EduCourseSelectionService;
import com.scu.eduadmin.vo.StudentRosterVO; 
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate; 
import org.springframework.web.bind.annotation.*;
import java.util.List; 
import java.util.Map;

@RestController
@RequestMapping("/api/course-selections")
@RequiredArgsConstructor
public class CourseSelectionController {
  
  private final EduCourseSelectionService service;
  private final JdbcTemplate jdbcTemplate; 

  @GetMapping
  public ApiResponse<Page<EduCourseSelection>> page(@RequestParam(defaultValue = "1") long pageNum,
                                                    @RequestParam(defaultValue = "10") long pageSize,
                                                    @RequestParam(required = false) Long studentId,
                                                    @RequestParam(required = false) Long teachingClassId) {
    LambdaQueryWrapper<EduCourseSelection> query = new LambdaQueryWrapper<EduCourseSelection>()
        .eq(studentId != null, EduCourseSelection::getStudentId, studentId)
        .eq(teachingClassId != null, EduCourseSelection::getTeachingClassId, teachingClassId);
    return ApiResponse.success(service.page(new Page<>(pageNum, pageSize), query));
  }

  @PostMapping("/select")
  public ApiResponse<EduCourseSelection> select(@Valid @RequestBody CourseSelectRequest request) {
    return ApiResponse.success(service.selectCourse(request.getStudentId(), request.getTeachingClassId()));
  }

  @PostMapping
  public ApiResponse<EduCourseSelection> create(@RequestBody EduCourseSelection body) {
    service.save(body);
    return ApiResponse.success(body);
  }

  @PutMapping("/{id}")
  public ApiResponse<EduCourseSelection> update(@PathVariable Long id, @RequestBody EduCourseSelection body) {
    body.setId(id);
    service.updateById(body);
    return ApiResponse.success(body);
  }

  @DeleteMapping("/{id}")
  public ApiResponse<Void> remove(@PathVariable Long id) {
    service.removeById(id);
    return ApiResponse.success();
  }

  @PostMapping("/drop")
  public ApiResponse<Void> drop(@Valid @RequestBody CourseSelectRequest request) {
    service.dropCourse(request.getStudentId(), request.getTeachingClassId());
    return ApiResponse.success();
  }

  @GetMapping("/my-courses")
  public ApiResponse<List<Map<String, Object>>> myCourses(@RequestParam Long studentId) {
      String sql = """
          SELECT
              selection_id AS selectionId,
              teaching_class_id AS teachingClassId,
              selection_status AS selectionStatus,
              selected_at AS selectedAt,
              course_code AS courseCode,
              course_name AS courseName,
              credit,
              teacher_name AS teacherName,
              semester_name AS semesterName,
              teaching_class_code AS teachingClassCode
          FROM v_student_course_selection_detail
          WHERE student_id = ?
          ORDER BY selected_at DESC
          """;
      return ApiResponse.success(jdbcTemplate.queryForList(sql, studentId));
  }

  @GetMapping("/options")
  public ApiResponse<List<Map<String, Object>>> options() {
      String sql = """
          SELECT
              d.selection_id AS selectionId,
              d.student_id AS studentId,
              s.student_no AS studentNo,
              s.student_name AS studentName,
              d.teaching_class_id AS teachingClassId,
              d.course_name AS courseName,
              d.teaching_class_code AS teachingClassCode,
              d.semester_name AS semesterName,
              d.selection_status AS selectionStatus
          FROM v_student_course_selection_detail d
          JOIN edu_student s ON s.id = d.student_id
          ORDER BY d.semester_name DESC, d.course_name, s.student_no
          """;
      return ApiResponse.success(jdbcTemplate.queryForList(sql));
  }

 
  @GetMapping("/roster")
  public ApiResponse<List<StudentRosterVO>> getRoster(
          @RequestParam Long teachingClassId,
          @RequestParam(required = false) String keyword) {
      
     
      StringBuilder sqlBuilder = new StringBuilder("""
          SELECT 
              s.id AS studentId,
              s.student_no AS studentNo,
              s.student_name AS studentName,
              cls.class_name AS className,
              cs.selection_status AS status
          FROM edu_course_selection cs
          JOIN edu_student s ON cs.student_id = s.id
          JOIN edu_class cls ON s.class_id = cls.id
          WHERE cs.teaching_class_id = ?
            AND cs.selection_status = 'selected'
          """);
      
      List<Object> params = new java.util.ArrayList<>();
      params.add(teachingClassId);
      
    
      if (keyword != null && !keyword.trim().isEmpty()) {
          sqlBuilder.append(" AND (s.student_name LIKE CONCAT('%', ?, '%') OR s.student_no LIKE CONCAT('%', ?, '%')) ");
          params.add(keyword.trim());
          params.add(keyword.trim());
      }
      
      sqlBuilder.append(" ORDER BY cls.class_name, s.student_no");

      List<StudentRosterVO> roster = jdbcTemplate.query(sqlBuilder.toString(), (rs, rowNum) -> {
          StudentRosterVO vo = new StudentRosterVO();
          vo.setStudentId(rs.getLong("studentId"));
          vo.setStudentNo(rs.getString("studentNo"));
          vo.setStudentName(rs.getString("studentName"));
          vo.setClassName(rs.getString("className"));
          vo.setStatus(rs.getString("status"));
          return vo;
      }, params.toArray());

      return ApiResponse.success(roster);
  }
}
