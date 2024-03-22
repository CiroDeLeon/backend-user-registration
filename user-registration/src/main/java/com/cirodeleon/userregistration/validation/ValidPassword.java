package com.cirodeleon.userregistration.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "El formato de la contraseña no es válido. Debe contener al menos una letra minúscula, una mayúscula, un número y un carácter especial y un tamaño minimo de 8 caracteres";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}