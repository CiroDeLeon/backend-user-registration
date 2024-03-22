package com.cirodeleon.userregistration.utils;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {

    /**
     * Encripta una contraseña utilizando BCrypt.
     *
     * @param password La contraseña en texto plano.
     * @return La contraseña encriptada.
     */
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Verifica una contraseña en texto plano contra una encriptada con BCrypt.
     *
     * @param password        La contraseña en texto plano.
     * @param hashedPassword  La contraseña encriptada para verificar.
     * @return true si las contraseñas coinciden, false en caso contrario.
     */
    public boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
