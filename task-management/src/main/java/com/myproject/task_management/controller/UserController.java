package com.myproject.task_management.controller;


import com.myproject.task_management.entity.Role;
import com.myproject.task_management.entity.User;
import com.myproject.task_management.repository.RoleRepository;
import com.myproject.task_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    private UserRepository userRepositry;

    private RoleRepository roleRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepositry, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepositry = userRepositry;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    //http://localhost:8080/user/add-user/ADMIN
    @PostMapping("/add-user/{roleType}")
    public User addUser(@PathVariable String roleType) {

        User user = new User();
        user.setUsername("john1");
        user.setEmail("john1@ptc");
        user.setPassword(passwordEncoder.encode("pwd123"));


        Role role;
        if (roleType.equalsIgnoreCase("ADMIN")) {
            role = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Role not found"));
        } else {
            role = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Role not found"));
        }

        user.getRoles().add(role);

        return userRepositry.save(user);

    }

    //http://localhost:8080/user/all
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepositry.findAll();
    }

    //http://localhost:8080/user/admin
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminApi() {
        return "Admin only";
    }
}
