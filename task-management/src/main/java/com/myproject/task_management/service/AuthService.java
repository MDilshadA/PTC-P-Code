package com.myproject.task_management.service;

import com.myproject.task_management.dto.request.LoginRequest;
import com.myproject.task_management.dto.request.RegisterRequest;
import com.myproject.task_management.dto.response.AuthResponse;

public interface AuthService {

    void register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}

