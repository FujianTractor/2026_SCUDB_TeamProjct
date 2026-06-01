package com.scu.eduadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.eduadmin.common.ApiResponse;
import com.scu.eduadmin.entity.EduClassroom;
import com.scu.eduadmin.service.EduClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
public class ClassroomController {
  private final EduClassroomService service;

  @GetMapping
  public ApiResponse<Page<EduClassroom>> page(@RequestParam(defaultValue = "1") long pageNum,
                                              @RequestParam(defaultValue = "10") long pageSize,
                                              @RequestParam(required = false) String building) {
    LambdaQueryWrapper<EduClassroom> query = new LambdaQueryWrapper<EduClassroom>()
        .like(StringUtils.hasText(building), EduClassroom::getBuilding, building);
    return ApiResponse.success(service.page(new Page<>(pageNum, pageSize), query));
  }

  @PostMapping
  public ApiResponse<EduClassroom> create(@RequestBody EduClassroom body) {
    service.save(body);
    return ApiResponse.success(body);
  }

  @PutMapping("/{id}")
  public ApiResponse<EduClassroom> update(@PathVariable Long id, @RequestBody EduClassroom body) {
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
