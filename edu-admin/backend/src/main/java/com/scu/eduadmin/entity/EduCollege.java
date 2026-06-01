package com.scu.eduadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_college")
public class EduCollege extends BaseEntity {
  private String collegeCode;
  private String collegeName;
  private String deanName;
  private String phone;
}
