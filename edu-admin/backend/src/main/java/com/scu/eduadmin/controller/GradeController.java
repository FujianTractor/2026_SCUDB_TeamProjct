package com.scu.eduadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.eduadmin.common.ApiResponse;
import com.scu.eduadmin.dto.GradeBatchRequest;
import com.scu.eduadmin.entity.EduGrade;
import com.scu.eduadmin.service.EduGradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
public class GradeController {
  private final EduGradeService service;

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
}
