package com.scu.eduadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.eduadmin.common.ApiResponse;
import com.scu.eduadmin.entity.EduClass;
import com.scu.eduadmin.service.EduClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class ClassController {
  private final EduClassService service;

  @GetMapping
  public ApiResponse<Page<EduClass>> page(@RequestParam(defaultValue = "1") long pageNum,
                                          @RequestParam(defaultValue = "10") long pageSize,
                                          @RequestParam(required = false) Long majorId,
                                          @RequestParam(required = false) String keyword) {
    LambdaQueryWrapper<EduClass> query = new LambdaQueryWrapper<EduClass>()
        .eq(majorId != null, EduClass::getMajorId, majorId)
        .and(StringUtils.hasText(keyword), q -> q.like(EduClass::getClassName, keyword).or().like(EduClass::getClassCode, keyword));
    return ApiResponse.success(service.page(new Page<>(pageNum, pageSize), query));
  }

  @PostMapping
  public ApiResponse<EduClass> create(@RequestBody EduClass body) {
    service.save(body);
    return ApiResponse.success(body);
  }

  @PutMapping("/{id}")
  public ApiResponse<EduClass> update(@PathVariable Long id, @RequestBody EduClass body) {
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
