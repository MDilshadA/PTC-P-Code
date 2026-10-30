package com.myproject.task_management.controller;

import com.myproject.task_management.config.JwtUtil;
import com.myproject.task_management.dto.request.LoginRequest;
import com.myproject.task_management.dto.request.RefreshTokenRequest;
import com.myproject.task_management.dto.request.RegisterRequest;
import com.myproject.task_management.dto.response.ApiResponse;
import com.myproject.task_management.dto.response.AuthResponse;
import com.myproject.task_management.entity.RefreshToken;
import com.myproject.task_management.security.CustomUserDetailsService;
import com.myproject.task_management.service.AuthService;
import com.myproject.task_management.serviceImpl.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    private final RefreshTokenService refreshTokenService;

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    //http://localhost:8080/api/auth/register
    @PostMapping("/register")
    public ApiResponse register(
            @Valid @RequestBody RegisterRequest request) {  //, BindingResult result

//        if(result.hasErrors()){
//
//            List<String> errors = result.getAllErrors()
//                    .stream()
//                    .map(e -> e.getDefaultMessage())
//                    .toList();
//
//            return ResponseEntity.badRequest().body(errors);
//        }


        authService.register(request);
        return new ApiResponse("User registered successfully");
    }
    //http://localhost:8080/api/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse token = authService.login(request);
        return ResponseEntity.ok(token);
    }


    //http://localhost:8080/api/auth/refresh-token

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(
            @RequestBody RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenService
                .verifyExpiration(request.getRefreshToken());

//        UserDetails userDetails =
//                customUserDetailsService.loadUserByUsername(
//                        refreshToken.getUser().getUsername()
//                );

        String newAccessToken = jwtUtil.generateToken(refreshToken.getUser().getUsername());

        refreshTokenService.revokeToken(refreshToken.getToken());
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(refreshToken.getUser());


        return ResponseEntity.ok(
                new AuthResponse(newAccessToken, newRefreshToken.getToken())
        );
    }

}

