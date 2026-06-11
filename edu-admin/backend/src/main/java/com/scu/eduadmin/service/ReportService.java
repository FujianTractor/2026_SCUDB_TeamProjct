package com.scu.eduadmin.service;

import com.scu.eduadmin.mapper.ReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {
  private final ReportMapper reportMapper;

  public List<Map<String, Object>> studentSchedule(Long studentId, Long semesterId) {
    return reportMapper.studentSchedule(studentId, semesterId);
  }

  public List<Map<String, Object>> teacherRoster(Long teacherId, Long teachingClassId) {
    return reportMapper.teacherRoster(teacherId, teachingClassId);
  }

  public List<Map<String, Object>> gradeStatistics(Long courseId, Long teacherId, Long teachingClassId) {
    return reportMapper.gradeStatistics(courseId, teacherId, teachingClassId);
  }

  public List<Map<String, Object>> completedCredits(Long studentId) {
    return reportMapper.completedCredits(studentId);
  }

  public List<Map<String, Object>> dashboardStats() {
    return reportMapper.dashboardStats();
  }
}
