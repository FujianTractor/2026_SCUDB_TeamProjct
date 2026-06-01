package com.scu.eduadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.eduadmin.common.ApiResponse;
import com.scu.eduadmin.dto.CourseSelectRequest;
import com.scu.eduadmin.entity.EduCourseSelection;
import com.scu.eduadmin.service.EduCourseSelectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course-selections")
@RequiredArgsConstructor
public class CourseSelectionController {
  private final EduCourseSelectionService service;

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
}
