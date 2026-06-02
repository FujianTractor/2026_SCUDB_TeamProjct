package com.scu.eduadmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scu.eduadmin.entity.EduStudent;
import com.scu.eduadmin.vo.StudentVO;

public interface EduStudentService extends IService<EduStudent> {

    Page<StudentVO> selectStudentPage(long pageNum, long pageSize, Long classId, String keyword);
}
