package com.scu.eduadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUser extends BaseEntity {
  private String username;
  private String passwordHash;
  private String realName;
  private String userType;
  private Long relatedId;
  private String userStatus;
  private LocalDateTime lastLoginAt;
}
