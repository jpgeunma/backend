package com.jpmarket.config.jwt;

public interface JwtProperties {
    String SECRET = "{}";
    int EXPIRATION_TIME = 3000;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
