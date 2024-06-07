package com.wanted.challenge.member.security.refreshToken.service;


import com.wanted.challenge.member.security.refreshToken.domain.RefreshToken;
import com.wanted.challenge.member.security.refreshToken.exception.TokenException;
import com.wanted.challenge.member.security.refreshToken.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.wanted.challenge.member.security.refreshToken.exception.TokenErrorCode.NO_SUCH_TOKEN;


@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public void save(String email, String refreshToken, long expiration) {
        refreshTokenRepository.save(RefreshToken.builder()
                .email(email)
                .refreshToken(refreshToken)
                .expiration(expiration)
                .build());
    }

    public boolean isRefreshTokenExpired(String email) {
        return !refreshTokenRepository.existsById(email);
    }

    public void delete(String email) {
        refreshTokenRepository.deleteById(email);
    }
    public boolean isSameToken(String email, String requestedRefreshToken){
        RefreshToken refreshToken = refreshTokenRepository.findById(email).orElseThrow(()->new TokenException(NO_SUCH_TOKEN));
        return refreshToken.isSameToken(requestedRefreshToken);
    }
}
