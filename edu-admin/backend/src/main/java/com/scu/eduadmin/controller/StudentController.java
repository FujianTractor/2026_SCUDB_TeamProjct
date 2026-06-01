package com.scu.eduadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.eduadmin.common.ApiResponse;
import com.scu.eduadmin.entity.EduStudent;
import com.scu.eduadmin.service.EduStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
  private final EduStudentService service;

  @GetMapping
  public ApiResponse<Page<EduStudent>> page(@RequestParam(defaultValue = "1") long pageNum,
                                            @RequestParam(defaultValue = "10") long pageSize,
                                            @RequestParam(required = false) Long classId,
                                            @RequestParam(required = false) String keyword) {
    LambdaQueryWrapper<EduStudent> query = new LambdaQueryWrapper<EduStudent>()
        .eq(classId != null, EduStudent::getClassId, classId)
        .and(StringUtils.hasText(keyword), q -> q.like(EduStudent::getStudentName, keyword).or().like(EduStudent::getStudentNo, keyword));
    return ApiResponse.success(service.page(new Page<>(pageNum, pageSize), query));
  }

  @GetMapping("/{id}")
  public ApiResponse<EduStudent> detail(@PathVariable Long id) {
    return ApiResponse.success(service.getById(id));
  }

  @PostMapping
  public ApiResponse<EduStudent> create(@RequestBody EduStudent body) {
    service.save(body);
    return ApiResponse.success(body);
  }

  @PutMapping("/{id}")
  public ApiResponse<EduStudent> update(@PathVariable Long id, @RequestBody EduStudent body) {
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
