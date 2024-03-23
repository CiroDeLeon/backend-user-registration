package com.cirodeleon.userregistration.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {
	@NotBlank(message = "El correo no puede estar vacío.")    
    private String email;
	@NotBlank(message = "El password no puede estar vacío.")
    private String password;
}
