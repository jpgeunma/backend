package com.jpmarket.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jpmarket.domain.user.User;
import com.jpmarket.domain.user.UserRepository;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Autowired
    UserRepository userRepository;

    @Value("${jpmarget.app.jwtSecret}")
    private String jwtScret;

    @Value("${jpmarget.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(User user) {

        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis()+ jwtExpirationMs))
                .withClaim("id", user.getId())
                .withClaim("name", user.getName())
                .sign(Algorithm.HMAC512(jwtScret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtScret).parseClaimsJwt(token).getBody().getSubject();
    }
    public boolean validateJwtToken(String authToken) {
        try{
            Jwts.parserBuilder().setSigningKey(jwtScret).build().parseClaimsJwt(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
