package com.myproject.task_management.serviceImpl;

import com.myproject.task_management.config.JwtUtil;
import com.myproject.task_management.dto.request.LoginRequest;
import com.myproject.task_management.dto.request.RegisterRequest;
import com.myproject.task_management.dto.response.AuthResponse;
import com.myproject.task_management.entity.RefreshToken;
import com.myproject.task_management.entity.Role;
import com.myproject.task_management.entity.User;
import com.myproject.task_management.repository.RoleRepository;
import com.myproject.task_management.repository.UserRepository;
import com.myproject.task_management.security.CustomUserDetailsService;
import com.myproject.task_management.service.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private JwtUtil jwtUtil;

    private CustomUserDetailsService customUserDetailsService;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final RefreshTokenService refreshTokenService;

    public AuthServiceImpl(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService, UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public void register(RegisterRequest request) {

        //if possible, we can think of cache here to reduce the number of database calls,
        // but for now we will keep it simple and check the database directly

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

//        here we set user role USER by default, later we can add an admin role and assign it to some users
        // Fetch roles from the database based on the roles provided in the request
        Set<Role> roles = request.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                .collect(Collectors.toSet());


        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.getRoles().addAll(roles);

        userRepository.save(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        //here Authentication completes successfully, we can generate JWT token and refresh token for the user

        String accessToken = jwtUtil.generateToken(authentication.getName());

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found AuthServiceImpl.login(LoginRequest request):"));
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken.getToken());
    }


}

