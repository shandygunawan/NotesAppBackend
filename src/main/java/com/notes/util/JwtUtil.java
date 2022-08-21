package com.notes.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.notes.config.YAMLConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Autowired
    private YAMLConfig yamlConfig;

    public String generateToken(String username) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withIssuer(yamlConfig.getJwtIssuer())
                .sign(Algorithm.HMAC256(yamlConfig.getJwtSecret()));
    }

    public String validateTokenAndRetrieveUsername(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(yamlConfig.getJwtSecret()))
                .withIssuer(yamlConfig.getJwtIssuer()).build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }

}
