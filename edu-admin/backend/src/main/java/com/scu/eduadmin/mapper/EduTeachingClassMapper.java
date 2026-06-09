package com.scu.eduadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scu.eduadmin.entity.EduTeachingClass;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface EduTeachingClassMapper extends BaseMapper<EduTeachingClass> {

    @Select("SELECT selected_count FROM edu_teaching_class WHERE id = #{id}")
    Integer getSelectedCountById(Long id);

    @Update("UPDATE edu_teaching_class SET selected_count = selected_count + 1 " +
            "WHERE id = #{teachingClassId} AND selected_count < #{capacity}")
    int incrementSelectedCountIfUnderCapacity(@Param("teachingClassId") Long teachingClassId,
                                              @Param("capacity") Integer capacity);

    @Update("UPDATE edu_teaching_class SET selected_count = selected_count - 1 " +
            "WHERE id = #{teachingClassId} AND selected_count > 0")
    int decrementSelectedCount(@Param("teachingClassId") Long teachingClassId);
}
