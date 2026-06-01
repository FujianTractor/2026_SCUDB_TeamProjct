package com.scu.eduadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_major")
public class EduMajor extends BaseEntity {
  private Long collegeId;
  private String majorCode;
  private String majorName;
  private String degreeType;
}
