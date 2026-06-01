package com.scu.eduadmin.exception;

import com.scu.eduadmin.common.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

  @ExceptionHandler(Exception.class)
  public ApiResponse<Void> handleUnexpected(Exception ex) {
    return ApiResponse.fail(500, ex.getMessage());
  }
}
