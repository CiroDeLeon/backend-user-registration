package com.cirodeleon.userregistration.dto;



import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhoneDto {
	@NotBlank(message = "El número no puede estar vacío.")
    private String number;

    @NotBlank(message = "El código de ciudad no puede estar vacío.")
    private String citycode;

    @NotBlank(message = "El código de país no puede estar vacío.")
    private String countrycode;
}
