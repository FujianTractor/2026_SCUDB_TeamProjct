package com.scu.eduadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.scu.eduadmin.entity.EduClassroom;
import com.scu.eduadmin.entity.EduCourseSelection;
import com.scu.eduadmin.entity.EduGrade;
import com.scu.eduadmin.entity.EduStudent;
import com.scu.eduadmin.mapper.EduCourseSelectionMapper;
import com.scu.eduadmin.mapper.EduGradeMapper;
import com.scu.eduadmin.mapper.EduStudentMapper;
import com.scu.eduadmin.service.EduStudentService;
import com.scu.eduadmin.vo.StudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class EduStudentServiceImpl extends ServiceImpl<EduStudentMapper, EduStudent> implements EduStudentService {

    @Autowired
    private EduCourseSelectionMapper eduCourseSelectionMapper;

    @Autowired
    private EduGradeMapper eduGradeMapper;

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

    @Override
    @Transactional
    public boolean removeStudentCascade(Long studentId) {
        eduGradeMapper.delete(new QueryWrapper<EduGrade>().eq("student_id", studentId));
        eduCourseSelectionMapper.delete(new QueryWrapper<EduCourseSelection>().eq("student_id", studentId));
        return this.removeById(studentId);
    }

    @Override
    @Transactional
    public boolean updateStudentIdCascade(Long oldId, Long newId) {
        eduGradeMapper.update(null, new QueryWrapper<EduGrade>().eq("student_id", oldId).set("student_id", newId));
        eduCourseSelectionMapper.update(null, new QueryWrapper<EduCourseSelection>().eq("student_id", oldId).set("student_id", newId));
        EduStudent student = this.getById(oldId);
        student.setId(newId);
        this.removeById(oldId);
        return this.save(student);
    }
}
