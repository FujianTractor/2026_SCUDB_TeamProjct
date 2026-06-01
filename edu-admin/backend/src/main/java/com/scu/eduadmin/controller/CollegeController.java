package com.scu.eduadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.eduadmin.common.ApiResponse;
import com.scu.eduadmin.entity.EduCollege;
import com.scu.eduadmin.service.EduCollegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/colleges")
@RequiredArgsConstructor
public class CollegeController {
  private final EduCollegeService service;

  @GetMapping
  public ApiResponse<Page<EduCollege>> page(@RequestParam(defaultValue = "1") long pageNum,
                                            @RequestParam(defaultValue = "10") long pageSize,
                                            @RequestParam(required = false) String keyword) {
    LambdaQueryWrapper<EduCollege> query = new LambdaQueryWrapper<EduCollege>()
        .like(StringUtils.hasText(keyword), EduCollege::getCollegeName, keyword)
        .or(StringUtils.hasText(keyword), q -> q.like(EduCollege::getCollegeCode, keyword));
    return ApiResponse.success(service.page(new Page<>(pageNum, pageSize), query));
  }

  @GetMapping("/{id}")
  public ApiResponse<EduCollege> detail(@PathVariable Long id) {
    return ApiResponse.success(service.getById(id));
  }

  @PostMapping
  public ApiResponse<EduCollege> create(@RequestBody EduCollege body) {
    service.save(body);
    return ApiResponse.success(body);
  }

  @PutMapping("/{id}")
  public ApiResponse<EduCollege> update(@PathVariable Long id, @RequestBody EduCollege body) {
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
