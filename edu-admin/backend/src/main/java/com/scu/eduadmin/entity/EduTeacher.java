package com.scu.eduadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_teacher")
public class EduTeacher extends BaseEntity {
  private Long userId;
  private Long collegeId;
  private String teacherNo;
  private String teacherName;
  private String gender;
  private String title;
  private String phone;
  private String email;
  private String teacherStatus;
}
