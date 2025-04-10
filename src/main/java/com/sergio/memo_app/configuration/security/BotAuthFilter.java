package com.sergio.memo_app.configuration.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class BotAuthFilter extends OncePerRequestFilter {

    private final String botSecret;

    public BotAuthFilter(String botSecret) {
        this.botSecret = botSecret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("X-Internal-Auth");
        System.out.println("botSecret: " + botSecret);
        System.out.println("header: " + header);
        if (!botSecret.equals(header)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
