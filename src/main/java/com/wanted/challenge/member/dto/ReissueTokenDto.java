package com.wanted.challenge.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanted.challenge.member.validation.email.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReissueTokenDto {
    @Email
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime today;

    public Date getDate() {
        if (Objects.isNull(this.today)) {
            return null;
        }
        return java.sql.Timestamp.valueOf(today);
    }
}
