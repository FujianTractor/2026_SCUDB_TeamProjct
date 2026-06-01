package com.scu.eduadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.eduadmin.entity.EduClassroom;
import com.scu.eduadmin.mapper.EduClassroomMapper;
import com.scu.eduadmin.service.EduClassroomService;
import org.springframework.stereotype.Service;

@Service
public class EduClassroomServiceImpl extends ServiceImpl<EduClassroomMapper, EduClassroom> implements EduClassroomService {}
