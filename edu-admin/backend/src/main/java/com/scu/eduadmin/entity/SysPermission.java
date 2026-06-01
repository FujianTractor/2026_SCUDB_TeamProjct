package com.scu.eduadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_permission")
public class SysPermission extends BaseEntity {
  private String permissionCode;
  private String permissionName;
  private String menuPath;
  private Long parentId;
}
