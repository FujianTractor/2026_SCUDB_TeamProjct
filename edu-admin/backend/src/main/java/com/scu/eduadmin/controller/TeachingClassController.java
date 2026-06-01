package com.scu.eduadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.eduadmin.common.ApiResponse;
import com.scu.eduadmin.entity.EduTeachingClass;
import com.scu.eduadmin.service.EduTeachingClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teaching-classes")
@RequiredArgsConstructor
public class TeachingClassController {
  private final EduTeachingClassService service;

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
