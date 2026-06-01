package com.scu.eduadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_course_selection")
public class EduCourseSelection extends BaseEntity {
  private Long studentId;
  private Long teachingClassId;
  private String selectionStatus;
  private LocalDateTime selectedAt;
  private LocalDateTime droppedAt;
}
