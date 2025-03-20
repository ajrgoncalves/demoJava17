package com.example.demo;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.authentication.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;

    @Autowired
    public DefaultUserLoader(UserRepository userRepository, SecurityConfig securityConfig) {
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.findByUsername("admin") == null) {
            String encodedPassword = securityConfig.passwordEncoder().encode("defaultPassword");
            User user = new User("admin", encodedPassword, "ADMIN");
            userRepository.save(user);
            System.out.println("Default admin user created.");
        }
    }

}

