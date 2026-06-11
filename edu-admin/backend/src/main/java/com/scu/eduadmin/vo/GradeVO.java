package com.scu.eduadmin.vo;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class GradeVO {
    private Long selectionId;
    private String courseName;
    private BigDecimal credits;
    private String semester;
    private BigDecimal score;
}
