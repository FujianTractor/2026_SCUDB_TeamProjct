package com.scu.eduadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.eduadmin.common.ApiResponse;
import com.scu.eduadmin.entity.EduTeacher;
import com.scu.eduadmin.service.EduTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {
  private final EduTeacherService service;

  @GetMapping
  public ApiResponse<Page<EduTeacher>> page(@RequestParam(defaultValue = "1") long pageNum,
                                            @RequestParam(defaultValue = "10") long pageSize,
                                            @RequestParam(required = false) Long collegeId,
                                            @RequestParam(required = false) String keyword) {
    LambdaQueryWrapper<EduTeacher> query = new LambdaQueryWrapper<EduTeacher>()
        .eq(collegeId != null, EduTeacher::getCollegeId, collegeId)
        .and(StringUtils.hasText(keyword), q -> q.like(EduTeacher::getTeacherName, keyword).or().like(EduTeacher::getTeacherNo, keyword));
    return ApiResponse.success(service.page(new Page<>(pageNum, pageSize), query));
  }

  @PostMapping
  public ApiResponse<EduTeacher> create(@RequestBody EduTeacher body) {
    service.save(body);
    return ApiResponse.success(body);
  }

  @PutMapping("/{id}")
  public ApiResponse<EduTeacher> update(@PathVariable Long id, @RequestBody EduTeacher body) {
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
