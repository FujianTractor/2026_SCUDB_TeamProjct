package com.scu.eduadmin.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class GradeBatchRequest {
  @Valid
  @NotEmpty
  private List<GradeItemRequest> grades;
}
