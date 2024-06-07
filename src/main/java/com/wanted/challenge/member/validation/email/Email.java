package com.wanted.challenge.member.validation.email;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = EmailValidator.class)
public @interface Email {

    String message() default "이메일 형식에 맞춰서 요청해주세요";
    String value() default "dev@gmail.com";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
