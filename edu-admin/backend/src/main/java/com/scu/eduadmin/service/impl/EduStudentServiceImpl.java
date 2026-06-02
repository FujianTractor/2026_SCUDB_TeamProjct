package com.scu.eduadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.scu.eduadmin.entity.EduClassroom;
import com.scu.eduadmin.entity.EduStudent;
import com.scu.eduadmin.mapper.EduStudentMapper;
import com.scu.eduadmin.service.EduStudentService;
import com.scu.eduadmin.vo.StudentVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EduStudentServiceImpl extends ServiceImpl<EduStudentMapper, EduStudent> implements EduStudentService {

    @Override
    public Page<StudentVO> selectStudentPage(long pageNum, long pageSize, Long classId, String keyword) {
        Page<StudentVO> page = new Page<>(pageNum, pageSize);

        MPJLambdaWrapper<EduStudent> wrapper = new MPJLambdaWrapper<EduStudent>()
                .selectAll(EduStudent.class)
                .select(EduClassroom::getClassName)
                .leftJoin(EduClassroom.class, EduClassroom::getId, EduStudent::getClassId)
                .eq(classId != null, EduStudent::getClassId, classId)
                .and(StringUtils.hasText(keyword), q -> q
                        .like(EduStudent::getStudentName, keyword)
                        .or()
                        .like(EduStudent::getStudentNo, keyword))
                .orderByDesc(EduStudent::getId);

        return this.baseMapper.selectJoinPage(page, StudentVO.class, wrapper);
    }
}
