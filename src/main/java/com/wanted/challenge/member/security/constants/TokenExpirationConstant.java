package com.wanted.challenge.member.security.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public final class TokenExpirationConstant {

    @Value("${spring.jwt.time-unit}")
    private long timeUnit;
    @Value("${spring.jwt.token-expired.access.second}")

    private long accessSecond;

    @Value("${spring.jwt.token-expired.access.minute}")
    private long accessMinute;

    @Value("${spring.jwt.token-expired.access.hour}")
    private long accessHour;

    @Value("${spring.jwt.token-expired.refresh.second}")
    private long refreshSecond;

    @Value("${spring.jwt.token-expired.refresh.minute}")
    private long refreshMinute;

    @Value("${spring.jwt.token-expired.refresh.hour}")
    private long refreshHour;

    private long getAccessTokenExpiredTime() {
        return accessHour * accessMinute * accessSecond * timeUnit;
    }

    private long getRefreshTokenExpiredTime() {
        return refreshHour * refreshMinute * refreshSecond * timeUnit;
    }

    public long getRefreshTokenExpiredMinute() {
        return refreshHour * refreshMinute * refreshSecond;
    }

    public Date getAccessTokenExpiredDate(Date date) {
        return new Date(date.getTime() + getAccessTokenExpiredTime());
    }

    public Date getRefreshTokenExpiredDate(Date date) {
        return new Date(date.getTime() + getRefreshTokenExpiredTime());
    }

    private TokenExpirationConstant(){

    }
}
