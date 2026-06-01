package com.scu.eduadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.eduadmin.entity.EduCollege;
import com.scu.eduadmin.mapper.EduCollegeMapper;
import com.scu.eduadmin.service.EduCollegeService;
import org.springframework.stereotype.Service;

@Service
public class EduCollegeServiceImpl extends ServiceImpl<EduCollegeMapper, EduCollege> implements EduCollegeService {}
