package com.scu.eduadmin.controller;

import com.scu.eduadmin.common.ApiResponse;
import com.scu.eduadmin.service.EduGradeService;
import com.scu.eduadmin.vo.GradeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse<List<GradeVO>> getMyGrades(@RequestParam Long studentId) {
        String sql = """
            SELECT 
                cs.id AS selectionId,
                c.course_name AS courseName,
                c.credits AS credits,
                c.semester AS semester,
                g.score AS score
            FROM edu_grade g
            JOIN edu_course_selection cs ON g.selection_id = cs.id
            JOIN edu_teaching_class tc ON cs.teaching_class_id = tc.id
            JOIN edu_course c ON tc.course_id = c.id
            WHERE cs.student_id = ?
            ORDER BY c.semester DESC, c.course_name
            """;

        List<GradeVO> grades = jdbcTemplate.query(sql,
                (rs, rowNum) -> {
                    GradeVO vo = new GradeVO();
                    vo.setSelectionId(rs.getLong("selectionId"));
                    vo.setCourseName(rs.getString("courseName"));
                    vo.setCredits(rs.getInt("credits"));
                    vo.setSemester(rs.getString("semester"));
                    vo.setScore(rs.getBigDecimal("score"));
                    return vo;
                },
                studentId
        );

        return ApiResponse.success(grades);
    }
}
