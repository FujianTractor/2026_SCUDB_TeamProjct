package com.scu.eduadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scu.eduadmin.entity.EduTeachingClass;

public interface EduTeachingClassService extends IService<EduTeachingClass> {
  void removeChecked(Long id);
}
