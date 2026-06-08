package com.scu.eduadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.eduadmin.entity.EduStudent;
import com.scu.eduadmin.vo.StudentVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface EduStudentMapper extends BaseMapper<EduStudent> {

  @Select("""
      <script>
      SELECT
        s.id,
        s.student_no,
        s.student_name,
        s.gender,
        s.phone,
        s.email,
        s.class_id,
        c.class_name
      FROM edu_student s
      LEFT JOIN edu_class c ON c.id = s.class_id
      <where>
        <if test="classId != null">
          s.class_id = #{classId}
        </if>
        <if test="keyword != null and keyword != ''">
          AND (s.student_name LIKE CONCAT('%', #{keyword}, '%')
            OR s.student_no LIKE CONCAT('%', #{keyword}, '%'))
        </if>
      </where>
      ORDER BY s.id DESC
      </script>
      """)
  Page<StudentVO> selectStudentPage(Page<StudentVO> page,
                                    @Param("classId") Long classId,
                                    @Param("keyword") String keyword);
}
