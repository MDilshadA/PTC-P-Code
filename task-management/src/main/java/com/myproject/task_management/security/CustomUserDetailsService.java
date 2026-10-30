package com.myproject.task_management.security;

import com.myproject.task_management.entity.User;
import com.myproject.task_management.exception.BadRequestException;
import com.myproject.task_management.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + username));

        if (user.getUsername() == null || user.getPassword() == null) {
            try {
                throw new BadRequestException("User data is incomplete for username: " + username);
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }

        }
        return new CustomUserDetails(user);

    }

}

