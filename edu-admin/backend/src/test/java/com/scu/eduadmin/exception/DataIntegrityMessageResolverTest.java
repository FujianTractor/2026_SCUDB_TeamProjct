package com.scu.eduadmin.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataIntegrityMessageResolverTest {
  @Test
  void resolvesKnownForeignKeyConstraintFromNestedCause() {
    RuntimeException databaseError = new RuntimeException(
        "Cannot delete or update a parent row: a foreign key constraint fails "
            + "(`scu_edu_admin`.`edu_major`, CONSTRAINT `fk_major_college` FOREIGN KEY (`college_id`))");
    RuntimeException wrappedError = new RuntimeException("database operation failed", databaseError);

    assertEquals(
        "该学院下仍有专业，无法删除。请先删除或调整相关专业。",
        DataIntegrityMessageResolver.resolve(wrappedError, true)
    );
  }

  @Test
  void resolvesSelectionGradeConstraint() {
    RuntimeException databaseError = new RuntimeException(
        "Cannot delete parent row, constraint FK_GRADE_SELECTION");

    assertEquals(
        "该选课记录已有成绩，无法删除。请先删除相关成绩。",
        DataIntegrityMessageResolver.resolve(databaseError, true)
    );
  }

  @Test
  void returnsDeleteFallbackWithoutExposingDatabaseDetails() {
    RuntimeException databaseError = new RuntimeException(
        "SQLIntegrityConstraintViolationException: secret_table internal SQL");

    assertEquals(
        "该数据仍被其他业务数据使用，暂时无法删除。请先处理关联数据后再试。",
        DataIntegrityMessageResolver.resolve(databaseError, true)
    );
  }

  @Test
  void returnsWriteFallbackForNonDeleteOperation() {
    RuntimeException databaseError = new RuntimeException("Duplicate entry 'secret' for key 'uk_unknown'");

    assertEquals(
        "提交的数据与现有记录冲突，请检查重复内容或关联数据后再试。",
        DataIntegrityMessageResolver.resolve(databaseError, false)
    );
  }
}
