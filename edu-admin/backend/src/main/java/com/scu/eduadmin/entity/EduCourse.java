package com.scu.eduadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_course")
public class EduCourse extends BaseEntity {
  private Long collegeId;
  private String courseCode;
  private String courseName;
  private BigDecimal credit;
  private String courseType;
  private String courseStatus;
  private Long teacherId;
}
