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

   
    @Transactional
    public boolean removeTeacherCascade(Long teacherId) {
       
        long classCount = teachingUpClassMapper.selectCount(
                new QueryWrapper<EduTeachingClass>().eq("teacher_id", teacherId)
        );

       
        if (classCount > 0) {
            throw new BusinessException("该教师当前有授课班级，无法删除！请先调整排课或结课后重试。");
        }

       
        return this.removeById(teacherId);
    }

   
    @Override
    @Transactional
    public boolean removeById(Serializable id) {
       
        return removeTeacherCascade((Long) id);
    }
}
