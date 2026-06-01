package com.scu.eduadmin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.eduadmin.common.ApiResponse;
import com.scu.eduadmin.entity.EduSemester;
import com.scu.eduadmin.service.EduSemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/semesters")
@RequiredArgsConstructor
public class SemesterController {
  private final EduSemesterService service;

  @GetMapping
  public ApiResponse<Page<EduSemester>> page(@RequestParam(defaultValue = "1") long pageNum,
                                             @RequestParam(defaultValue = "10") long pageSize) {
    return ApiResponse.success(service.page(new Page<>(pageNum, pageSize)));
  }

  @PostMapping
  public ApiResponse<EduSemester> create(@RequestBody EduSemester body) {
    service.save(body);
    return ApiResponse.success(body);
  }

  @PutMapping("/{id}")
  public ApiResponse<EduSemester> update(@PathVariable Long id, @RequestBody EduSemester body) {
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
