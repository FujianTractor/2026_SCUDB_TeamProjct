package com.scu.eduadmin.exception;

import com.scu.eduadmin.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(BusinessException.class)
  public ApiResponse<Void> handleBusiness(BusinessException ex) {
    return ApiResponse.fail(ex.getCode(), ex.getMessage());
  }

  @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
  public ApiResponse<Void> handleValidation(Exception ex) {
    return ApiResponse.fail(422, ex.getMessage());
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ApiResponse<Void> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
    log.warn("Data integrity violation during {} {}", request.getMethod(), request.getRequestURI(), ex);
    boolean deleteOperation = "DELETE".equalsIgnoreCase(request.getMethod());
    return ApiResponse.fail(409, DataIntegrityMessageResolver.resolve(ex, deleteOperation));
  }

  @ExceptionHandler(Exception.class)
  public ApiResponse<Void> handleUnexpected(Exception ex, HttpServletRequest request) {
    log.error("Unexpected error during {} {}", request.getMethod(), request.getRequestURI(), ex);
    return ApiResponse.fail(500, "系统处理失败，请稍后重试或联系管理员。");
  }
}
