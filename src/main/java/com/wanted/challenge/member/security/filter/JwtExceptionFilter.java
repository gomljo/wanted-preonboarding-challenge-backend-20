package com.wanted.challenge.member.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.wanted.challenge.apiResponse.ApiResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            doFilter(request, response, filterChain);
        } catch (RuntimeException runtimeException) {
            response.setCharacterEncoding("UTF-8");
            objectMapper.writeValue(response.getWriter(), ApiResponse.error(HttpStatus.BAD_REQUEST.toString()));
        }
    }
}
