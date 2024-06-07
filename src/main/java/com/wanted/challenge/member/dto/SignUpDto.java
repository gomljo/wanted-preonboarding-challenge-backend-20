package com.wanted.challenge.member.dto;


import com.wanted.challenge.member.validation.email.Email;
import com.wanted.challenge.member.validation.name.Name;
import com.wanted.challenge.member.validation.password.Password;
import com.wanted.challenge.member.validation.role.RoleChecker;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class SignUpDto {
    @Email
    private String email;
    @Password
    private String password;
    @Name
    private String username;
    @NotNull
    private List<@RoleChecker String> roles;
}
