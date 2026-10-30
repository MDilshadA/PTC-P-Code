package com.myproject.task_management.config;

import com.myproject.task_management.entity.Role;
import com.myproject.task_management.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 *  This class initializes the database with default roles when the application starts.
 * It checks if the "ROLE_USER" and "ROLE_ADMIN" roles exist, and if not, it creates them.
 */
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {

            if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                roleRepository.save(new Role(null, "ROLE_USER"));
            }

            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                roleRepository.save(new Role(null, "ROLE_ADMIN"));
            }
        };
    }



}
