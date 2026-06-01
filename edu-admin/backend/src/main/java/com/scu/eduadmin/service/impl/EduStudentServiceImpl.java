package com.scu.eduadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.eduadmin.entity.EduStudent;
import com.scu.eduadmin.mapper.EduStudentMapper;
import com.scu.eduadmin.service.EduStudentService;
import org.springframework.stereotype.Service;

@Service
public class EduStudentServiceImpl extends ServiceImpl<EduStudentMapper, EduStudent> implements EduStudentService {}
