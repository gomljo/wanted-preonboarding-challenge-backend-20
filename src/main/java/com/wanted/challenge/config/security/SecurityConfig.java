package com.wanted.challenge.config.security;


import com.wanted.challenge.member.security.filter.JwtAuthenticationFilter;
import com.wanted.challenge.member.security.filter.JwtExceptionFilter;
import com.wanted.challenge.member.security.provider.JWTProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;

    public SecurityConfig(JWTProvider jwtProvider) {
        this.jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtProvider);
        this.jwtExceptionFilter = new JwtExceptionFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(
                        this.jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterBefore(this.jwtExceptionFilter, JwtAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        "/",
                        "/images/**",
                        "/js/**",
                        "/css/**",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v2/**",
                        "/v3/**",
                        "/**/member/signup",
                        "/**/member/signin"
                );
    }
}
