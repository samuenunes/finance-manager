package com.leumas.finance.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.leumas.finance.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.AlgorithmConstraints;
import java.time.Instant;
import java.util.Optional;

@Component
public class TokenService {
    @Value("${finance.security.secret}")
    private String secret;

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("id", user.getId())
                .withClaim("name", user.getName())
                .withExpiresAt(Instant.now().plusSeconds(3600 * 24))
                .withIssuedAt(Instant.now())
                .withIssuer("Finance Manager API")
                .sign(algorithm);
    }

    public Optional<JWTUserData> verifyToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT jwt = JWT.require(algorithm).build().verify(token);

            return Optional.of(JWTUserData.builder()
                    .id(jwt.getClaim("id").asLong())
                    .email(jwt.getSubject())
                    .build());
        }catch (JWTVerificationException e){
            return Optional.empty();
        }
    }
}
