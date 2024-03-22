package com.cirodeleon.userregistration.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


@ResponseStatus(value = HttpStatus.CONFLICT, reason = "El correo ya registrado")
public class EmailAlreadyRegisteredException extends RuntimeException {

    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }

    // Si necesitas manejar la serialización de la excepción (como para enviarla a través de servicios), podría necesitar un serialVersionUID
    // private static final long serialVersionUID = 1L;

    // También podrías añadir más construcciones o métodos si fuera necesario

}

