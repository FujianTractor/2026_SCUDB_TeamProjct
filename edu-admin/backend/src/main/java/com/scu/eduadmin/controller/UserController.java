package com.scu.eduadmin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.eduadmin.common.ApiResponse;
import com.scu.eduadmin.entity.SysPermission;
import com.scu.eduadmin.entity.SysRole;
import com.scu.eduadmin.entity.SysUser;
import com.scu.eduadmin.mapper.SysPermissionMapper;
import com.scu.eduadmin.mapper.SysRoleMapper;
import com.scu.eduadmin.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
  private final SysUserMapper userMapper;
  private final SysRoleMapper roleMapper;
  private final SysPermissionMapper permissionMapper;

  @GetMapping
  public ApiResponse<Page<SysUser>> page(@RequestParam(defaultValue = "1") long pageNum,
                                         @RequestParam(defaultValue = "10") long pageSize) {
    Page<SysUser> page = new Page<>(pageNum, pageSize);
    return ApiResponse.success(userMapper.selectPage(page, null));
  }

  @GetMapping("/roles")
  public ApiResponse<List<SysRole>> roles() {
    return ApiResponse.success(roleMapper.selectList(null));
  }

  @GetMapping("/permissions")
  public ApiResponse<List<SysPermission>> permissions() {
    return ApiResponse.success(permissionMapper.selectList(null));
  }

  @PostMapping
  public ApiResponse<SysUser> create(@RequestBody SysUser body) {
    userMapper.insert(body);
    return ApiResponse.success(body);
  }

  @PutMapping("/{id}")
  public ApiResponse<SysUser> update(@PathVariable Long id, @RequestBody SysUser body) {
    body.setId(id);
    userMapper.updateById(body);
    return ApiResponse.success(body);
  }

  @DeleteMapping("/{id}")
  public ApiResponse<Void> remove(@PathVariable Long id) {
    userMapper.deleteById(id);
    return ApiResponse.success();
  }
}
