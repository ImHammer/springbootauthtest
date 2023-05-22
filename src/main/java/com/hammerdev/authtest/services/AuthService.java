package com.hammerdev.authtest.services;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hammerdev.authtest.config.JwtConfig;
import com.hammerdev.authtest.entities.Person;

@Service
public class AuthService
{
    public static final String JWT_ISSUER = "http://myauthsystem.com";
    public static final String JWT_AUDIENCE = "MyAuthSystem";
    public static final String TOKEN_HEADER_NAME = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    
    @Autowired
    private JwtConfig jwtConfig;

    public @Nullable String extractToken(Map<String, String> headers)
    {
        if (headers.containsKey(TOKEN_HEADER_NAME) && headers.get(TOKEN_HEADER_NAME).startsWith(TOKEN_PREFIX)) {
            return headers.get(TOKEN_HEADER_NAME).substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public Long getUserId(String token) throws Exception
    {
        JWTVerifier jwtVerifier = JWT.require(getAlgorithm())
            .withIssuer(JWT_ISSUER)
            .withAudience(JWT_AUDIENCE)
            .build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        return Long.valueOf(decodedJWT.getSubject());
    }

    public @Nullable String createToken(Person user) throws IllegalArgumentException, JWTCreationException, UnsupportedEncodingException
    {
        return JWT.create()
            .withIssuer(JWT_ISSUER)
            .withAudience(JWT_AUDIENCE)
            .withSubject(String.valueOf(user.getId()))
            .withExpiresAt(new Date(System.currentTimeMillis() + jwtConfig.getExpires()))
            .sign(getAlgorithm());
    }

    private Algorithm getAlgorithm() throws IllegalArgumentException, UnsupportedEncodingException
    {
        return Algorithm.HMAC256(jwtConfig.getSecret());
    }
}
