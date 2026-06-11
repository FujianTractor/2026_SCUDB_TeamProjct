package com.scu.eduadmin.controller;

import com.scu.eduadmin.common.ApiResponse;
import com.scu.eduadmin.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
  private final ReportService reportService;

  @GetMapping("/dashboard")
  public ApiResponse<List<Map<String, Object>>> dashboard() {
    return ApiResponse.success(reportService.dashboardStats());
  }

  @GetMapping("/student-schedule")
  public ApiResponse<List<Map<String, Object>>> studentSchedule(@RequestParam Long studentId,
                                                                @RequestParam(required = false) Long semesterId) {
    return ApiResponse.success(reportService.studentSchedule(studentId, semesterId));
  }

  @GetMapping("/teacher-roster")
  public ApiResponse<List<Map<String, Object>>> teacherRoster(@RequestParam Long teacherId,
                                                              @RequestParam(required = false) Long teachingClassId) {
    return ApiResponse.success(reportService.teacherRoster(teacherId, teachingClassId));
  }

  @GetMapping("/grade-statistics")
  public ApiResponse<List<Map<String, Object>>> gradeStatistics(@RequestParam(required = false) Long courseId,
                                                                @RequestParam(required = false) Long teacherId,
                                                                @RequestParam(required = false) Long teachingClassId) {
    return ApiResponse.success(reportService.gradeStatistics(courseId, teacherId, teachingClassId));
  }

  @GetMapping("/completed-credits")
  public ApiResponse<List<Map<String, Object>>> completedCredits(@RequestParam Long studentId) {
    return ApiResponse.success(reportService.completedCredits(studentId));
  }
}
