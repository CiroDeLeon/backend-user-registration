package com.cirodeleon.userregistration.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Value("${app.password.regex}")
    private String passwordPattern;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        // Aquí puedes inicializar cualquier recurso necesario para tu validador.
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        return password.matches(passwordPattern);
    }
}
