package com.scu.eduadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_classroom")
public class EduClassroom extends BaseEntity {
  private String building;
  private String roomNo;
  private Integer capacity;
  private Integer hasMultimedia;
  private String classroomStatus;
}
