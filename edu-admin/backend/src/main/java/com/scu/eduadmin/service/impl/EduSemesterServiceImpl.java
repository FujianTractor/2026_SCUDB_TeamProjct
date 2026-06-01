package com.scu.eduadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.eduadmin.entity.EduSemester;
import com.scu.eduadmin.mapper.EduSemesterMapper;
import com.scu.eduadmin.service.EduSemesterService;
import org.springframework.stereotype.Service;

@Service
public class EduSemesterServiceImpl extends ServiceImpl<EduSemesterMapper, EduSemester> implements EduSemesterService {}
