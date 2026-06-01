package com.scu.eduadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.eduadmin.entity.EduMajor;
import com.scu.eduadmin.mapper.EduMajorMapper;
import com.scu.eduadmin.service.EduMajorService;
import org.springframework.stereotype.Service;

@Service
public class EduMajorServiceImpl extends ServiceImpl<EduMajorMapper, EduMajor> implements EduMajorService {}
