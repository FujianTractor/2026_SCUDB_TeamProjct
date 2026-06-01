package com.scu.eduadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.scu.eduadmin.mapper")
@SpringBootApplication
public class EduAdminApplication {
  public static void main(String[] args) {
    SpringApplication.run(EduAdminApplication.class, args);
  }
}
