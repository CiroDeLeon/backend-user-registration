package com.cirodeleon.userregistration.utils;


import com.cirodeleon.userregistration.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

import java.util.Date;

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
}
