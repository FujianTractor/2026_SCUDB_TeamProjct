package com.scu.eduadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.eduadmin.common.ApiResponse;
import com.scu.eduadmin.dto.GradeBatchRequest;
import com.scu.eduadmin.entity.EduGrade;
import com.scu.eduadmin.service.EduGradeService;
import com.scu.eduadmin.vo.GradeVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
public class GradeController {
    private final EduGradeService service;
    private final JdbcTemplate jdbcTemplate;

    @GetMapping
    public ApiResponse<Page<EduGrade>> page(@RequestParam(defaultValue = "1") long pageNum,
                                            @RequestParam(defaultValue = "10") long pageSize,
                                            @RequestParam(required = false) Long selectionId) {
        LambdaQueryWrapper<EduGrade> query = new LambdaQueryWrapper<EduGrade>()
                .eq(selectionId != null, EduGrade::getSelectionId, selectionId);
        return ApiResponse.success(service.page(new Page<>(pageNum, pageSize), query));
    }

    @PostMapping("/batch")
    public ApiResponse<Void> batch(@Valid @RequestBody GradeBatchRequest request) {
        service.batchUpsert(request.getGrades());
        return ApiResponse.success();
    }

    @GetMapping("/input-roster")
    public ApiResponse<List<java.util.Map<String, Object>>> inputRoster(@RequestParam Long teacherId,
                                                                         @RequestParam Long teachingClassId) {
        String sql = """
            SELECT
                cs.id AS selectionId,
                s.student_no AS studentNo,
                s.student_name AS studentName,
                cls.class_name AS className,
                c.course_name AS courseName,
                g.score AS score
            FROM edu_course_selection cs
            JOIN edu_student s ON s.id = cs.student_id
            JOIN edu_class cls ON cls.id = s.class_id
            JOIN edu_teaching_class tc ON tc.id = cs.teaching_class_id
            JOIN edu_course c ON c.id = tc.course_id
            LEFT JOIN edu_grade g ON g.selection_id = cs.id
            WHERE cs.selection_status = 'selected'
              AND tc.teacher_id = ?
              AND tc.id = ?
            ORDER BY cls.class_name, s.student_no
            """;
        return ApiResponse.success(jdbcTemplate.queryForList(sql, teacherId, teachingClassId));
    }

    @PostMapping
    public ApiResponse<EduGrade> create(@RequestBody EduGrade body) {
        service.save(body);
        return ApiResponse.success(body);
    }

    @PutMapping("/{id}")
    public ApiResponse<EduGrade> update(@PathVariable Long id, @RequestBody EduGrade body) {
        body.setId(id);
        service.updateById(body);
        return ApiResponse.success(body);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> remove(@PathVariable Long id) {
        service.removeById(id);
        return ApiResponse.success();
    }

    @GetMapping("/my-grades")
    public ApiResponse<List<GradeVO>> getMyGrades(@RequestParam Long studentId,
                                                  @RequestParam(required = false) String courseName,
                                                  @RequestParam(required = false) String semester) {
        
        // 使用 StringBuilder 动态拼接 SQL，防止注入并提高性能
        StringBuilder sqlBuilder = new StringBuilder("""
            SELECT 
                selection_id AS selectionId,
                course_name AS courseName,
                credit AS credits,
                semester_name AS semester,
                score
            FROM v_student_grade_detail
            WHERE student_id = ?
        """);
        
        List<Object> params = new ArrayList<>();
        params.add(studentId);

        // 如果传入了课程名称，进行模糊查询
        if (StringUtils.hasText(courseName)) {
            sqlBuilder.append(" AND course_name LIKE ?");
            params.add("%" + courseName + "%");
        }

        // 如果传入了学期，进行精确匹配
        if (StringUtils.hasText(semester)) {
            sqlBuilder.append(" AND semester_name LIKE ?");
            params.add("%" + semester + "%");
        }

        sqlBuilder.append(" ORDER BY start_date DESC, course_name");

        List<GradeVO> grades = jdbcTemplate.query(sqlBuilder.toString(),
                (rs, rowNum) -> {
                    GradeVO vo = new GradeVO();
                    vo.setSelectionId(rs.getLong("selectionId"));
                    vo.setCourseName(rs.getString("courseName"));
                    vo.setCredits(rs.getBigDecimal("credits"));
                    vo.setSemester(rs.getString("semester"));
                    vo.setScore(rs.getBigDecimal("score"));
                    return vo;
                },
                params.toArray()
        );

        return ApiResponse.success(grades);
    }
}
