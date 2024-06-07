package com.wanted.challenge.member.security.refreshToken.repository;


import com.wanted.challenge.member.security.refreshToken.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

}
