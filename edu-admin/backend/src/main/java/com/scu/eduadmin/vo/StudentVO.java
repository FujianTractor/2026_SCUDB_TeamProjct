package com.scu.eduadmin.vo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class StudentVO {
    private Long id;
    private String studentNo;
    private String studentName;
    private String gender;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private Long classId;
    private String className;
}
