package com.example.Lab1TBD.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class JwtUtil {

    // Clave secreta y algoritmo
    private static final String SECRET = "yo";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);

    // Método para crear un JWT con rol
    public String create(String username, String role) {
        return JWT.create()
                .withSubject(username)
                .withClaim("role", role) // Agregar rol como claim
                .withIssuer("tbd")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000)) // 15 minutos
                .sign(ALGORITHM);
    }

    // Método para validar un JWT
    public boolean isValid(String jwt) {
        try {
            JWT.require(ALGORITHM)
                    .build()
                    .verify(jwt);
            return true;
        } catch (JWTVerificationException e) {
            System.out.println("Token inválido: " + e.getMessage());
            return false;
        }
    }

    // Método para extraer el nombre de usuario del JWT
    public String getUsername(String jwt) {
        DecodedJWT decodedJWT = JWT.require(ALGORITHM)
                .build()
                .verify(jwt);
        return decodedJWT.getSubject();
    }

    // Método para extraer el rol del JWT
    public String getRole(String jwt) {
        DecodedJWT decodedJWT = JWT.require(ALGORITHM)
                .build()
                .verify(jwt);
        return decodedJWT.getClaim("role").asString();
    }
}
