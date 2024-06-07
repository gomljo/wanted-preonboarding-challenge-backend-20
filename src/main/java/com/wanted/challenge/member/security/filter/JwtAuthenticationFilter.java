package com.wanted.challenge.member.security.filter;

import com.wanted.challenge.member.security.provider.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String ACCESS_TOKEN_HEADER = "Authorization";
    private static final String REFRESH_TOKEN_HEADER = "RefreshToken";
    private final JWTProvider jwtProvider;

    public JwtAuthenticationFilter(JWTProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = this.resolveTokenFromRequest(request, ACCESS_TOKEN_HEADER);
        String refreshToken = this.resolveTokenFromRequest(request, REFRESH_TOKEN_HEADER);

        if (StringUtils.hasText(accessToken)) {
            String wantToValidate = accessToken;
            if (StringUtils.hasText(refreshToken)) {
                wantToValidate = refreshToken;
            }
            this.jwtProvider.validateToken(wantToValidate, new Date());

            Authentication auth = this.jwtProvider.getAuthentication(wantToValidate);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);

    }

    private String resolveTokenFromRequest(HttpServletRequest request, String headerName) {
        return request.getHeader(headerName);
    }
}