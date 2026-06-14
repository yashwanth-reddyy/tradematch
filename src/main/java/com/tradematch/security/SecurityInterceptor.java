package com.tradematch.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");

        // Simple check to ensure an OAuth token format is simulation valid
        if (authHeader == null || !authHeader.startsWith("Bearer secret_oauth_token_123")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("OAuth Authentication Failed: Missing or Invalid Bearer Token.");
            return false;
        }
        return true;
    }
}