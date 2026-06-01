package com.scu.eduadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.eduadmin.common.ApiResponse;
import com.scu.eduadmin.entity.EduCourse;
import com.scu.eduadmin.service.EduCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
  private final EduCourseService service;

  @GetMapping
  public ApiResponse<Page<EduCourse>> page(@RequestParam(defaultValue = "1") long pageNum,
                                           @RequestParam(defaultValue = "10") long pageSize,
                                           @RequestParam(required = false) String keyword,
                                           @RequestParam(required = false) String courseType) {
    LambdaQueryWrapper<EduCourse> query = new LambdaQueryWrapper<EduCourse>()
        .eq(StringUtils.hasText(courseType), EduCourse::getCourseType, courseType)
        .and(StringUtils.hasText(keyword), q -> q.like(EduCourse::getCourseName, keyword).or().like(EduCourse::getCourseCode, keyword));
    return ApiResponse.success(service.page(new Page<>(pageNum, pageSize), query));
  }

  @PostMapping
  public ApiResponse<EduCourse> create(@RequestBody EduCourse body) {
    service.save(body);
    return ApiResponse.success(body);
  }

  @PutMapping("/{id}")
  public ApiResponse<EduCourse> update(@PathVariable Long id, @RequestBody EduCourse body) {
    body.setId(id);
    service.updateById(body);
    return ApiResponse.success(body);
  }

  @DeleteMapping("/{id}")
  public ApiResponse<Void> remove(@PathVariable Long id) {
    service.removeById(id);
    return ApiResponse.success();
  }
}
