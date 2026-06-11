package com.scu.eduadmin.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ReportMapper {
  List<Map<String, Object>> studentSchedule(@Param("studentId") Long studentId, @Param("semesterId") Long semesterId);
  List<Map<String, Object>> teacherRoster(@Param("teacherId") Long teacherId, @Param("teachingClassId") Long teachingClassId);
  List<Map<String, Object>> gradeStatistics(@Param("courseId") Long courseId,
                                            @Param("teacherId") Long teacherId,
                                            @Param("teachingClassId") Long teachingClassId);
  List<Map<String, Object>> completedCredits(@Param("studentId") Long studentId);
  List<Map<String, Object>> dashboardStats();
}
