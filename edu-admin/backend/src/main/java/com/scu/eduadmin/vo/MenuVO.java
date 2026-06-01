package com.scu.eduadmin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuVO {
  private String permissionCode;
  private String permissionName;
  private String menuPath;
}
