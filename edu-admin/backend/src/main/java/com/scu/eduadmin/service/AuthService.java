package com.scu.eduadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scu.eduadmin.dto.LoginRequest;
import com.scu.eduadmin.entity.SysPermission;
import com.scu.eduadmin.entity.SysRole;
import com.scu.eduadmin.entity.SysRolePermission;
import com.scu.eduadmin.entity.SysUser;
import com.scu.eduadmin.entity.SysUserRole;
import com.scu.eduadmin.exception.BusinessException;
import com.scu.eduadmin.mapper.SysPermissionMapper;
import com.scu.eduadmin.mapper.SysRoleMapper;
import com.scu.eduadmin.mapper.SysRolePermissionMapper;
import com.scu.eduadmin.mapper.SysUserMapper;
import com.scu.eduadmin.mapper.SysUserRoleMapper;
import com.scu.eduadmin.vo.LoginVO;
import com.scu.eduadmin.vo.MenuVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final SysUserMapper userMapper;
  private final SysUserRoleMapper userRoleMapper;
  private final SysRoleMapper roleMapper;
  private final SysRolePermissionMapper rolePermissionMapper;
  private final SysPermissionMapper permissionMapper;
  private final PasswordEncoder passwordEncoder;

  public LoginVO login(LoginRequest request) {
    SysUser user = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
        .eq(SysUser::getUsername, request.getUsername()));
    if (user == null || !"active".equals(user.getUserStatus())) {
      throw new BusinessException(401, "用户不存在或已禁用");
    }
    if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
      throw new BusinessException(401, "用户名或密码错误");
    }

    List<Long> roleIds = userRoleMapper.selectList(new LambdaQueryWrapper<SysUserRole>()
            .eq(SysUserRole::getUserId, user.getId()))
        .stream().map(SysUserRole::getRoleId).toList();
    List<SysRole> roles = roleIds.isEmpty() ? List.of() : roleMapper.selectBatchIds(roleIds);

    List<Long> permissionIds = roleIds.isEmpty() ? List.of() : rolePermissionMapper
        .selectList(new LambdaQueryWrapper<SysRolePermission>().in(SysRolePermission::getRoleId, roleIds))
        .stream().map(SysRolePermission::getPermissionId).distinct().toList();
    List<MenuVO> menus = permissionIds.isEmpty() ? List.of() : permissionMapper.selectBatchIds(permissionIds)
        .stream()
        .filter(permission -> permission.getMenuPath() != null)
        .map(permission -> new MenuVO(permission.getPermissionCode(), permission.getPermissionName(), permission.getMenuPath()))
        .toList();

    user.setLastLoginAt(LocalDateTime.now());
    userMapper.updateById(user);

    return new LoginVO(
        UUID.randomUUID().toString(),
        user.getId(),
        user.getUsername(),
        user.getRealName(),
        user.getUserType(),
        user.getRelatedId(),
        roles.stream().map(SysRole::getRoleCode).toList(),
        menus
    );
  }
}
