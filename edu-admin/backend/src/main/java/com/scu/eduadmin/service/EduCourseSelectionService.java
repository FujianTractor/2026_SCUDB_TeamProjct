package com.scu.eduadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scu.eduadmin.entity.EduCourseSelection;

public interface EduCourseSelectionService extends IService<EduCourseSelection> {
  EduCourseSelection selectCourse(Long studentId, Long teachingClassId);
  void dropCourse(Long studentId, Long teachingClassId);
}
