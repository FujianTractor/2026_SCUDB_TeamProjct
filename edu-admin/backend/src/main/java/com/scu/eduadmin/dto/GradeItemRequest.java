package com.scu.eduadmin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GradeItemRequest {
  @NotNull
  private Long selectionId;
  @NotNull
  private BigDecimal score;
}
