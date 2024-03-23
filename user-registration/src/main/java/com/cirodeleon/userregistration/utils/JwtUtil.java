package com.cirodeleon.userregistration.utils;


import com.cirodeleon.userregistration.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
@Component
public class JwtUtil {

	private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(User user) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(new Date(nowMillis + 3600000)) // 1 hora de expiración
                .signWith(key)
                .compact();
    }
    
    // Método para extraer el nombre de usuario (email) del token
    public String extractUsername(String token) {
        return this.getClaimsFromToken(token).getSubject();
    }

    // Método para validar el token (puedes expandirlo según tus necesidades, por ejemplo, verificar roles)
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    // Extrae los claims de un token
    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // Verifica si el token ha expirado
    private boolean isTokenExpired(String token) {
        final Date expiration = this.getClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }
}
