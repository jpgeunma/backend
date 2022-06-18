package com.jpmarket.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jpmarket.config.jwt.JwtProperties;
import com.jpmarket.domain.user.User;
import com.jpmarket.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public String getToken(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다"));


        return createToken(user);
    }

    public String createToken(User user) {

        String jwtToken = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", user.getId())
                .withClaim("nickname", user.getEmail())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        return jwtToken;
    }
}
