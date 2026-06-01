package com.scu.eduadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_semester")
public class EduSemester extends BaseEntity {
  private String semesterCode;
  private String semesterName;
  private LocalDate startDate;
  private LocalDate endDate;
  private Integer isCurrent;
}
