package com.scu.eduadmin.exception;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

final class DataIntegrityMessageResolver {
  private static final Map<String, String> CONSTRAINT_MESSAGES = Map.ofEntries(
      Map.entry("fk_major_college", "该学院下仍有专业，无法删除。请先删除或调整相关专业。"),
      Map.entry("fk_teacher_college", "该学院下仍有教师，无法删除。请先调整相关教师的所属学院。"),
      Map.entry("fk_course_college", "该学院下仍有课程，无法删除。请先删除或调整相关课程。"),
      Map.entry("fk_class_major", "该专业下仍有班级，无法删除。请先删除或调整相关班级。"),
      Map.entry("fk_student_class", "该班级下仍有学生，无法删除。请先删除或调整相关学生。"),
      Map.entry("fk_teaching_class_course", "该课程已用于教学班，无法删除。请先删除或调整相关教学班。"),
      Map.entry("fk_teaching_class_teacher", "该教师仍有授课教学班，无法删除。请先调整相关教学班。"),
      Map.entry("fk_teaching_class_semester", "该学期已用于教学班，无法删除。请先删除或调整相关教学班。"),
      Map.entry("fk_schedule_teaching_class", "该教学班仍有排课安排，无法删除。请先删除相关排课安排。"),
      Map.entry("fk_schedule_classroom", "该教室仍有排课安排，无法删除。请先调整或删除相关排课安排。"),
      Map.entry("fk_selection_student", "该学生仍有选课记录，无法删除。请先处理相关选课记录。"),
      Map.entry("fk_selection_teaching_class", "该教学班仍有选课记录，无法删除。请先处理相关选课记录。"),
      Map.entry("fk_grade_selection", "该选课记录已有成绩，无法删除。请先删除相关成绩。"),
      Map.entry("fk_user_role_user", "该用户仍绑定角色，无法删除。请先解除角色绑定。"),
      Map.entry("fk_student_user", "该用户仍关联学生档案，无法删除。请先解除学生档案关联。"),
      Map.entry("fk_teacher_user", "该用户仍关联教师档案，无法删除。请先解除教师档案关联。"),
      Map.entry("fk_log_user", "该用户仍有关联操作日志，无法删除。建议停用该用户，而不是直接删除。"),
      Map.entry("fk_permission_parent", "该权限仍有下级权限，无法删除。请先处理下级权限。"),
      Map.entry("fk_user_role_role", "该角色仍分配给用户，无法删除。请先解除用户角色绑定。"),
      Map.entry("fk_role_permission_role", "该角色仍绑定权限，无法删除。请先解除权限绑定。"),
      Map.entry("fk_role_permission_permission", "该权限仍分配给角色，无法删除。请先解除角色权限绑定。")
  );

  private DataIntegrityMessageResolver() {
  }

  static String resolve(Throwable throwable, boolean deleteOperation) {
    String details = collectMessages(throwable).toLowerCase(Locale.ROOT);
    for (Map.Entry<String, String> entry : CONSTRAINT_MESSAGES.entrySet()) {
      if (details.contains(entry.getKey())) {
        return entry.getValue();
      }
    }
    if (deleteOperation) {
      return "该数据仍被其他业务数据使用，暂时无法删除。请先处理关联数据后再试。";
    }
    return "提交的数据与现有记录冲突，请检查重复内容或关联数据后再试。";
  }

  private static String collectMessages(Throwable throwable) {
    StringBuilder messages = new StringBuilder();
    Set<Throwable> visited = Collections.newSetFromMap(new IdentityHashMap<>());
    Throwable current = throwable;
    while (current != null && visited.add(current)) {
      if (current.getMessage() != null) {
        messages.append(' ').append(current.getMessage());
      }
      current = current.getCause();
    }
    return messages.toString();
  }
}
