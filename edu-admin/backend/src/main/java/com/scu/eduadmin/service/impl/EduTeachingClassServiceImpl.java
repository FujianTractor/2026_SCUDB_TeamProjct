package com.scu.eduadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.eduadmin.entity.EduCourseSelection;
import com.scu.eduadmin.entity.EduTeachingClass;
import com.scu.eduadmin.exception.BusinessException;
import com.scu.eduadmin.mapper.EduCourseSelectionMapper;
import com.scu.eduadmin.mapper.EduTeachingClassMapper;
import com.scu.eduadmin.service.EduTeachingClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EduTeachingClassServiceImpl extends ServiceImpl<EduTeachingClassMapper, EduTeachingClass> implements EduTeachingClassService {
  private final EduCourseSelectionMapper selectionMapper;

  @Override
  @Transactional
  public void removeChecked(Long id) {
    Long count = selectionMapper.selectCount(new LambdaQueryWrapper<EduCourseSelection>()
        .eq(EduCourseSelection::getTeachingClassId, id));
    if (count > 0) {
      throw new BusinessException("教学班已有选课记录，不允许删除");
    }
    removeById(id);
  }
}
