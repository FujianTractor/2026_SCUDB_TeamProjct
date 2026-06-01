package com.scu.eduadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_teaching_class")
public class EduTeachingClass extends BaseEntity {
  private Long courseId;
  private Long teacherId;
  private Long semesterId;
  private String teachingClassCode;
  private Integer capacity;
  private Integer selectedCount;
  private String classStatus;
}
