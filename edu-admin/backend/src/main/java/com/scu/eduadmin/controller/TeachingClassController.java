package com.scu.eduadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.eduadmin.common.ApiResponse;
import com.scu.eduadmin.entity.EduTeachingClass;
import com.scu.eduadmin.service.EduTeachingClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teaching-classes")
@RequiredArgsConstructor
public class TeachingClassController {
  private final EduTeachingClassService service;
  private final JdbcTemplate jdbcTemplate;

  @GetMapping
  public ApiResponse<Page<EduTeachingClass>> page(@RequestParam(defaultValue = "1") long pageNum,
                                                  @RequestParam(defaultValue = "10") long pageSize,
                                                  @RequestParam(required = false) Long courseId,
                                                  @RequestParam(required = false) Long teacherId,
                                                  @RequestParam(required = false) Long semesterId) {
    LambdaQueryWrapper<EduTeachingClass> query = new LambdaQueryWrapper<EduTeachingClass>()
        .eq(courseId != null, EduTeachingClass::getCourseId, courseId)
        .eq(teacherId != null, EduTeachingClass::getTeacherId, teacherId)
        .eq(semesterId != null, EduTeachingClass::getSemesterId, semesterId);
    return ApiResponse.success(service.page(new Page<>(pageNum, pageSize), query));
  }

  @GetMapping("/options")
  public ApiResponse<List<Map<String, Object>>> options(@RequestParam(required = false) Long teacherId,
                                                        @RequestParam(required = false) String keyword,
                                                        @RequestParam(required = false) Boolean openOnly) {
    String sql = """
        SELECT
          id,
          course_id AS courseId,
          teacher_id AS teacherId,
          semester_id AS semesterId,
          teaching_class_code AS teachingClassCode,
          capacity,
          selected_count AS selectedCount,
          class_status AS classStatus,
          course_code AS courseCode,
          course_name AS courseName,
          teacher_name AS teacherName,
          semester_name AS semesterName
        FROM v_teaching_class_detail
        WHERE (? IS NULL OR teacher_id = ?)
          AND (? IS NULL OR class_status = 'open')
          AND (
            ? IS NULL
            OR teaching_class_code LIKE CONCAT('%', ?, '%')
            OR course_code LIKE CONCAT('%', ?, '%')
            OR course_name LIKE CONCAT('%', ?, '%')
            OR teacher_name LIKE CONCAT('%', ?, '%')
          )
        ORDER BY is_current DESC, course_name, teaching_class_code
        """;
    String kw = (keyword == null || keyword.trim().isEmpty()) ? null : keyword.trim();
    Boolean onlyOpen = Boolean.TRUE.equals(openOnly) ? Boolean.TRUE : null;
    return ApiResponse.success(jdbcTemplate.queryForList(sql, teacherId, teacherId, onlyOpen,
        kw, kw, kw, kw, kw));
  }

  @PostMapping
  public ApiResponse<EduTeachingClass> create(@RequestBody EduTeachingClass body) {
    service.save(body);
    return ApiResponse.success(body);
  }

  @PutMapping("/{id}")
  public ApiResponse<EduTeachingClass> update(@PathVariable Long id, @RequestBody EduTeachingClass body) {
    body.setId(id);
    service.updateById(body);
    return ApiResponse.success(body);
  }

  @DeleteMapping("/{id}")
  public ApiResponse<Void> remove(@PathVariable Long id) {
    service.removeChecked(id);
    return ApiResponse.success();
  }
}
