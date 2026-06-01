package com.scu.eduadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_grade")
public class EduGrade extends BaseEntity {
  private Long selectionId;
  private BigDecimal score;
  private BigDecimal gradePoint;
  private String gradeStatus;
  private LocalDateTime submittedAt;
}
