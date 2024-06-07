package com.wanted.challenge.member.service;

import com.wanted.challenge.member.dto.ReissueTokenDto;
import com.wanted.challenge.member.dto.SignInDto;
import com.wanted.challenge.member.dto.SignUpDto;
import com.wanted.challenge.member.dto.TokenDto;
import com.wanted.challenge.member.exception.MemberRuntimeException;
import com.wanted.challenge.member.model.Member;
import com.wanted.challenge.member.repository.MemberRepository;
import com.wanted.challenge.member.security.provider.JWTProvider;
import com.wanted.challenge.member.security.refreshToken.exception.TokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.wanted.challenge.member.exception.MemberError.*;
import static com.wanted.challenge.member.security.refreshToken.exception.TokenErrorCode.EMPTY_TOKEN;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;


    @Transactional
    public void register(SignUpDto signUpDto) {

        if (memberRepository.existsByEmail(signUpDto.getEmail())) {
            throw new MemberRuntimeException(ALREADY_JOINED_CUSTOMER);
        }

        String encodedPassword = this.passwordEncoder.encode(signUpDto.getPassword());
        memberRepository.save(Member.builder()
                .email(signUpDto.getEmail())
                .password(encodedPassword)
                .name(signUpDto.getUsername())
                .roles(signUpDto.getRoles())
                .build());
    }

    public TokenDto signIn(SignInDto signInRequest) {
        Member memberInformation = memberRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new MemberRuntimeException(NO_SUCH_MEMBER));

        if (!this.passwordEncoder.matches(signInRequest.getPassword(), memberInformation.getPassword())) {
            throw new MemberRuntimeException(PASSWORD_NOT_MATCH);
        }

        if (Objects.isNull(signInRequest.getDate())) {
            throw new MemberRuntimeException(TIME_EMPTY);
        }

        String accessToken = this.jwtProvider.generateAccessToken(memberInformation.getEmail(), signInRequest.getDate());
        String refreshToken = this.jwtProvider.issueRefreshToken(memberInformation.getEmail(), signInRequest.getDate());

        return new TokenDto(accessToken, refreshToken);
    }

    public Member searchBy(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberRuntimeException(NO_SUCH_MEMBER));
    }

    public void logout(String email) {
        this.jwtProvider.deleteRefreshToken(email);
    }

    public String reissue(String refreshToken, ReissueTokenDto reissueTokenDto) {
        if (refreshToken.isEmpty()) {
            throw new TokenException(EMPTY_TOKEN);
        }
        if (Objects.isNull(reissueTokenDto.getDate())) {
            throw new MemberRuntimeException(TIME_EMPTY);
        }
        return this.jwtProvider.reissue(reissueTokenDto.getEmail(), refreshToken, reissueTokenDto.getDate());
    }


}
