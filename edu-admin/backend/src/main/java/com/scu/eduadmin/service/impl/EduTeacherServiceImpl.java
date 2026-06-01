package com.scu.eduadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.eduadmin.entity.EduTeacher;
import com.scu.eduadmin.mapper.EduTeacherMapper;
import com.scu.eduadmin.service.EduTeacherService;
import org.springframework.stereotype.Service;

@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {}
