package com.scu.eduadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.eduadmin.dto.GradeItemRequest;
import com.scu.eduadmin.entity.EduGrade;
import com.scu.eduadmin.mapper.EduGradeMapper;
import com.scu.eduadmin.service.EduGradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EduGradeServiceImpl extends ServiceImpl<EduGradeMapper, EduGrade> implements EduGradeService {
  @Override
  @Transactional
  public void batchUpsert(List<GradeItemRequest> grades) {
    for (GradeItemRequest item : grades) {
      EduGrade grade = getOne(new LambdaQueryWrapper<EduGrade>()
          .eq(EduGrade::getSelectionId, item.getSelectionId()), false);
      if (grade == null) {
        grade = new EduGrade();
        grade.setSelectionId(item.getSelectionId());
      }
      grade.setScore(item.getScore());
      grade.setGradePoint(toGradePoint(item.getScore()));
      grade.setGradeStatus("submitted");
      grade.setSubmittedAt(LocalDateTime.now());
      saveOrUpdate(grade);
    }
  }

  private BigDecimal toGradePoint(BigDecimal score) {
    if (score.compareTo(BigDecimal.valueOf(60)) < 0) {
      return BigDecimal.ZERO.setScale(2);
    }
    BigDecimal point = score.subtract(BigDecimal.valueOf(50)).divide(BigDecimal.TEN, 2, RoundingMode.HALF_UP);
    return point.min(BigDecimal.valueOf(4.00)).setScale(2, RoundingMode.HALF_UP);
  }
}
