package com.scu.eduadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

@Service
public class EduStudentServiceImpl extends ServiceImpl<EduStudentMapper, EduStudent> implements EduStudentService {

    @Autowired
    private EduCourseSelectionMapper eduCourseSelectionMapper;

    @Autowired
    private EduGradeMapper eduGradeMapper;

    @Override
    public Page<StudentVO> selectStudentPage(long pageNum, long pageSize, Long classId, String keyword) {
        Page<StudentVO> page = new Page<>(pageNum, pageSize);
        return this.baseMapper.selectStudentPage(page, classId, keyword);
    }

    @Override
    @Transactional
    public boolean removeStudentCascade(Long studentId) {
        eduGradeMapper.delete(new QueryWrapper<EduGrade>()
                .inSql("selection_id", "SELECT id FROM edu_course_selection WHERE student_id = " + studentId));
        eduCourseSelectionMapper.delete(new QueryWrapper<EduCourseSelection>().eq("student_id", studentId));
        return this.removeById(studentId);
    }

    @Override
    @Transactional
    public boolean updateStudentIdCascade(Long oldId, Long newId) {
        eduCourseSelectionMapper.update(null, new UpdateWrapper<EduCourseSelection>()
                .eq("student_id", oldId)
                .set("student_id", newId));
        EduStudent student = this.getById(oldId);
        student.setId(newId);
        this.removeById(oldId);
        return this.save(student);
    }
}
