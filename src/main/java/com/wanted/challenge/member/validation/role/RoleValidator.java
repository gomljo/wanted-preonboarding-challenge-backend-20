package com.wanted.challenge.member.validation.role;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class RoleValidator implements ConstraintValidator<RoleChecker, String> {
    @Override
    public void initialize(RoleChecker constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Arrays.stream(com.wanted.challenge.member.model.Role.values())
                .anyMatch(role -> role.name().equals(value));
    }
}
