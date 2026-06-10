package com.scu.eduadmin.vo;

import lombok.Data;


@Data
public class StudentRosterVO {
    private Long studentId;
    private String studentNo;
    private String studentName;
    private String className; 
    private String status;   
}
