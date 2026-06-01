package com.scu.eduadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_student")
public class EduStudent extends BaseEntity {
  private Long userId;
  private Long classId;
  private String studentNo;
  private String studentName;
  private String gender;
  private String phone;
  private String email;
  private Integer enrollmentYear;
  private String studentStatus;
}
