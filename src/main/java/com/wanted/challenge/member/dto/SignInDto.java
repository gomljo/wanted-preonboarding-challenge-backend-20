package com.wanted.challenge.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.wanted.challenge.member.validation.email.Email;
import com.wanted.challenge.member.validation.password.Password;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInDto {
    @Email
    private String email;
    @Password
    private String password;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime today;

    public Date getDate() {
        if (Objects.isNull(this.today)) {
            return null;
        }
        return java.sql.Timestamp.valueOf(this.today);
    }

}
