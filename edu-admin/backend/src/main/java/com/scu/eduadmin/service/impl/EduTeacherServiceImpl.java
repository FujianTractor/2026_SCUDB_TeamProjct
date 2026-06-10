package com.scu.eduadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.eduadmin.entity.EduTeacher;
import com.scu.eduadmin.entity.EduTeachingClass; 
import com.scu.eduadmin.mapper.EduTeacherMapper;
import com.scu.eduadmin.mapper.EduTeachingClassMapper; 
import com.scu.eduadmin.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.scu.eduadmin.common.BusinessException;

@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Autowired
    private EduTeachingClassMapper teachingClassMapper;

 
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(java.io.Serializable id) {
       
        QueryWrapper<EduTeachingClass> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", id);
        Integer count = teachingClassMapper.selectCount(queryWrapper);

       
        if (count != null && count > 0) {
            throw new BusinessException("该教师仍有授课任务（" + count + "个教学班），无法删除！");
        }

       
        return super.removeById(id);
    }
}
