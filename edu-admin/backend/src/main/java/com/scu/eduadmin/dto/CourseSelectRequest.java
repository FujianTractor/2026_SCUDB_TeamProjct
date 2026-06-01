package com.scu.eduadmin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseSelectRequest {
  @NotNull
  private Long studentId;
  @NotNull
  private Long teachingClassId;
}
