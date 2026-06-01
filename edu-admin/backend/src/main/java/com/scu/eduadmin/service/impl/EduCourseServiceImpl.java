package com.scu.eduadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.eduadmin.entity.EduCourse;
import com.scu.eduadmin.mapper.EduCourseMapper;
import com.scu.eduadmin.service.EduCourseService;
import org.springframework.stereotype.Service;

@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {}
