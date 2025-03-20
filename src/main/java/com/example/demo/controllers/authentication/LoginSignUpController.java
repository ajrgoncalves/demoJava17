package com.example.demo.controllers.authentication;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.authentication.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginSignUpController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Authentication failed");
        }
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username, @RequestParam String password) {
        String encodedPassword = securityConfig.passwordEncoder().encode(password);
        User user = new User(username, encodedPassword);
        userRepository.save(user);
        return "redirect:/login";
    }
}
