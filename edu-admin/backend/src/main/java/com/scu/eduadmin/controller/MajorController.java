package com.scu.eduadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.eduadmin.common.ApiResponse;
import com.scu.eduadmin.entity.EduMajor;
import com.scu.eduadmin.service.EduMajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/majors")
@RequiredArgsConstructor
public class MajorController {
  private final EduMajorService service;

  @GetMapping
  public ApiResponse<Page<EduMajor>> page(@RequestParam(defaultValue = "1") long pageNum,
                                          @RequestParam(defaultValue = "10") long pageSize,
                                          @RequestParam(required = false) Long collegeId,
                                          @RequestParam(required = false) String keyword) {
    LambdaQueryWrapper<EduMajor> query = new LambdaQueryWrapper<EduMajor>()
        .eq(collegeId != null, EduMajor::getCollegeId, collegeId)
        .and(StringUtils.hasText(keyword), q -> q.like(EduMajor::getMajorName, keyword).or().like(EduMajor::getMajorCode, keyword));
    return ApiResponse.success(service.page(new Page<>(pageNum, pageSize), query));
  }

  @PostMapping
  public ApiResponse<EduMajor> create(@RequestBody EduMajor body) {
    service.save(body);
    return ApiResponse.success(body);
  }

  @PutMapping("/{id}")
  public ApiResponse<EduMajor> update(@PathVariable Long id, @RequestBody EduMajor body) {
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
