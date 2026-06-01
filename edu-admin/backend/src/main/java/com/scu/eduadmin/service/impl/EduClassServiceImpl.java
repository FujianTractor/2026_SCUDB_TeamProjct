package com.scu.eduadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.eduadmin.entity.EduClass;
import com.scu.eduadmin.mapper.EduClassMapper;
import com.scu.eduadmin.service.EduClassService;
import org.springframework.stereotype.Service;

@Service
public class EduClassServiceImpl extends ServiceImpl<EduClassMapper, EduClass> implements EduClassService {}
