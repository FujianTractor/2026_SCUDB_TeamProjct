package com.scu.eduadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.eduadmin.entity.EduCourse;
import com.scu.eduadmin.entity.EduCourseSelection;
import com.scu.eduadmin.entity.EduGrade;
import com.scu.eduadmin.entity.EduTeachingClass;
import com.scu.eduadmin.entity.EduTeachingClassSchedule;
import com.scu.eduadmin.exception.BusinessException;
import com.scu.eduadmin.mapper.EduCourseMapper;
import com.scu.eduadmin.mapper.EduCourseSelectionMapper;
import com.scu.eduadmin.mapper.EduGradeMapper;
import com.scu.eduadmin.mapper.EduTeachingClassMapper;
import com.scu.eduadmin.mapper.EduTeachingClassScheduleMapper;
import com.scu.eduadmin.service.EduCourseSelectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EduCourseSelectionServiceImpl extends ServiceImpl<EduCourseSelectionMapper, EduCourseSelection> implements EduCourseSelectionService {
    private final EduTeachingClassMapper teachingClassMapper;
    private final EduCourseMapper courseMapper;
    private final EduTeachingClassScheduleMapper scheduleMapper;
    private final EduGradeMapper eduGradeMapper;

    @Override
    @Transactional
    public EduCourseSelection selectCourse(Long studentId, Long teachingClassId) {
        EduTeachingClass teachingClass = teachingClassMapper.selectById(teachingClassId);
        if (teachingClass == null || !"open".equals(teachingClass.getClassStatus())) {
            throw new BusinessException("教学班不存在或未开放");
        }
        EduCourse course = courseMapper.selectById(teachingClass.getCourseId());
        if (course == null || !"active".equals(course.getCourseStatus())) {
            throw new BusinessException("课程不存在或已停用");
        }

        EduCourseSelection oldSelection = getOne(new LambdaQueryWrapper<EduCourseSelection>()
                .eq(EduCourseSelection::getStudentId, studentId)
                .eq(EduCourseSelection::getTeachingClassId, teachingClassId), false);
        if (oldSelection != null && "selected".equals(oldSelection.getSelectionStatus())) {
            throw new BusinessException("不能重复选课");
        }
        ensureNoScheduleConflict(studentId, teachingClassId);

        int updatedRows = teachingClassMapper.incrementSelectedCountIfUnderCapacity(
                teachingClassId,
                teachingClass.getCapacity()
        );
        if (updatedRows == 0) {
            throw new BusinessException("课程容量已满，选课失败");
        }

        EduCourseSelection selection = oldSelection == null ? new EduCourseSelection() : oldSelection;
        selection.setStudentId(studentId);
        selection.setTeachingClassId(teachingClassId);
        selection.setSelectionStatus("selected");
        selection.setSelectedAt(LocalDateTime.now());
        selection.setDroppedAt(null);
        saveOrUpdate(selection);

        return selection;
    }

    @Override
    @Transactional
    public void dropCourse(Long studentId, Long teachingClassId) {
        EduCourseSelection selection = getOne(new LambdaQueryWrapper<EduCourseSelection>()
                .eq(EduCourseSelection::getStudentId, studentId)
                .eq(EduCourseSelection::getTeachingClassId, teachingClassId), false);
        if (selection == null || !"selected".equals(selection.getSelectionStatus())) {
            throw new BusinessException("未找到有效选课记录");
        }

        EduGrade grade = eduGradeMapper.selectOne(new LambdaQueryWrapper<EduGrade>()
                .eq(EduGrade::getSelectionId, selection.getId()));

        if (grade != null) {
            if (grade.getScore() != null && grade.getScore().compareTo(BigDecimal.ZERO) > 0) {
                throw new BusinessException("该课程已录入成绩，无法退课，请联系教务处处理！");
            } else {
                eduGradeMapper.deleteById(grade.getId());
            }
        }

        selection.setSelectionStatus("dropped");
        selection.setDroppedAt(LocalDateTime.now());
        updateById(selection);

        // 使用原子性减法操作，并检查是否成功
        int updatedRows = teachingClassMapper.decrementSelectedCount(teachingClassId);
        if (updatedRows == 0) {
            throw new BusinessException("退课失败，当前人数状态异常");
        }
    }

    private void ensureNoScheduleConflict(Long studentId, Long targetTeachingClassId) {
        List<EduTeachingClassSchedule> targetSchedules = scheduleMapper.selectList(
                new LambdaQueryWrapper<EduTeachingClassSchedule>()
                        .eq(EduTeachingClassSchedule::getTeachingClassId, targetTeachingClassId));
        if (targetSchedules.isEmpty()) {
            return;
        }
        List<Long> selectedTeachingClassIds = list(new LambdaQueryWrapper<EduCourseSelection>()
                        .eq(EduCourseSelection::getStudentId, studentId)
                        .eq(EduCourseSelection::getSelectionStatus, "selected"))
                .stream().map(EduCourseSelection::getTeachingClassId).toList();
        if (selectedTeachingClassIds.isEmpty()) {
            return;
        }
        List<EduTeachingClassSchedule> selectedSchedules = scheduleMapper.selectList(
                new LambdaQueryWrapper<EduTeachingClassSchedule>()
                        .in(EduTeachingClassSchedule::getTeachingClassId, selectedTeachingClassIds));
        for (EduTeachingClassSchedule target : targetSchedules) {
            for (EduTeachingClassSchedule selected : selectedSchedules) {
                boolean sameDay = target.getWeekday().equals(selected.getWeekday());
                boolean overlap = target.getStartSection() <= selected.getEndSection()
                        && selected.getStartSection() <= target.getEndSection();
                if (sameDay && overlap) {
                    throw new BusinessException("选课时间冲突");
                }
            }
        }
    }
}
