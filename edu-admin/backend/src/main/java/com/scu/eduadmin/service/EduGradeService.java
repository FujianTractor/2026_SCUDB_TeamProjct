package com.scu.eduadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scu.eduadmin.dto.GradeItemRequest;
import com.scu.eduadmin.entity.EduGrade;

import java.util.List;

public interface EduGradeService extends IService<EduGrade> {
  void batchUpsert(List<GradeItemRequest> grades);
}
