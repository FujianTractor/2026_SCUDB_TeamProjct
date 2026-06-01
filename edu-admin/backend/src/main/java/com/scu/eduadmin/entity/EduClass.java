package com.scu.eduadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_class")
public class EduClass extends BaseEntity {
  private Long majorId;
  private String classCode;
  private String className;
  private Integer gradeYear;
}
