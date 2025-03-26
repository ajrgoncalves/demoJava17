package com.example.demo.security;

import com.example.demo.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@WebFilter(urlPatterns = "/api/**")
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Get the token from the Authorization header
        String token = request.getHeader("Authorization");

        if (token != null && JwtUtils.validateToken(token)) {
            String username = JwtUtils.getUsernameFromToken(token);
            if (username != null) {
                // Set the authenticated user in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, null));
            }
        }

        filterChain.doFilter(request, response);  // Continue with the filter chain
    }
}
