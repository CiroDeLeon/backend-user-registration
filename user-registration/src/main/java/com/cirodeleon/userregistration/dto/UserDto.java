package com.cirodeleon.userregistration.dto;

import java.util.HashSet;
import java.util.Set;

import com.cirodeleon.userregistration.entity.User;
import com.cirodeleon.userregistration.validation.ValidPassword;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
	@NotBlank(message = "El nombre no puede estar vacío.")
    private String name;

    @NotBlank(message = "El correo no puede estar vacío.")
    @Email(message = "Formato de correo inválido.")
    @Pattern(regexp = "^[\\w-\\.]+@[\\w-]+\\.cl$", message = "El correo debe ser un correo válido con dominio .cl")
    private String email;

    //@NotBlank(message = "La contraseña no puede estar vacía.")
    @ValidPassword
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    
    @Valid
    private Set<PhoneDto> phones;
}
