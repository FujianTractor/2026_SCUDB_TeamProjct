package com.scu.eduadmin.controller;

import com.scu.eduadmin.common.ApiResponse;
import com.scu.eduadmin.dto.LoginRequest;
import com.scu.eduadmin.service.AuthService;
import com.scu.eduadmin.vo.LoginVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  public ApiResponse<LoginVO> login(@Valid @RequestBody LoginRequest request) {
    return ApiResponse.success(authService.login(request));
  }
}
