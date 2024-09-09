package com.appointment_management.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppStartUpConfig {

    @Value("${spring.security.user.email}")
    private String adminEmail;

    @Value("${spring.security.user.password}")
    private String adminPassword;

    @Value("${spring.security.user.role}")
    private String adminRole;

    @Bean
    public CommandLineRunner initAdminUser(UserManagementService userManagementService) {
        return args -> {
            if (userManagementService.isUserDatabaseEmpty()) {
                userManagementService.createAdminUserIfNotExists(adminEmail, adminPassword, adminRole);
            }
        };
    }
}
