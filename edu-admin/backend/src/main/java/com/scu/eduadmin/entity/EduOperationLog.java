package com.scu.eduadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_operation_log")
public class EduOperationLog extends BaseEntity {
  private Long userId;
  private String moduleName;
  private String operationType;
  private String operationContent;
  private String ipAddress;
}
