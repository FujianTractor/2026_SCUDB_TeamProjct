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


import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 【核心修改】修复了原先 inSql 导致的删除失败问题
     */
    @Override
    @Transactional
    public boolean removeStudentCascade(Long studentId) {
        // 第一步：查出该学生所有的选课记录ID
        List<EduCourseSelection> selections = eduCourseSelectionMapper.selectList(
                new QueryWrapper<EduCourseSelection>().eq("student_id", studentId)
        );

        // 第二步：如果有选课记录，再根据这些ID去安全地删除成绩表数据
        if (selections != null && !selections.isEmpty()) {
            List<Long> selectionIds = selections.stream()
                    .map(EduCourseSelection::getId)
                    .collect(Collectors.toList());
            
            eduGradeMapper.delete(new QueryWrapper<EduGrade>().in("selection_id", selectionIds));
        }

        // 第三步：清空该学生的所有选课记录
        eduCourseSelectionMapper.delete(new QueryWrapper<EduCourseSelection>().eq("student_id", studentId));

        // 第四步：最后删除学生本身
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
