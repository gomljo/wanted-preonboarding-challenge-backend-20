package com.wanted.challenge.member.security.userDetailService;


import com.wanted.challenge.member.model.Member;
import com.wanted.challenge.member.repository.MemberRepository;
import com.wanted.challenge.member.security.userDetails.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public SecurityUser loadUserByUsername(String email) throws UsernameNotFoundException {
        Member memberInformation = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return new SecurityUser(memberInformation);
    }
}
