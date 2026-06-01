package com.scu.eduadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_teaching_class_schedule")
public class EduTeachingClassSchedule extends BaseEntity {
  private Long teachingClassId;
  private Long classroomId;
  private Integer weekday;
  private Integer startSection;
  private Integer endSection;
}
