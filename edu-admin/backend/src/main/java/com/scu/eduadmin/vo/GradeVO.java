package com.scu.eduadmin.vo;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class GradeVO {
    private Long selectionId;
    private String courseName;
    private Integer credits;
    private String semester;
    private BigDecimal score;
}
