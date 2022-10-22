package com.jpmarket.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jpmarket.config.auth.dto.CustomUserDetails;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils implements Serializable {
    private static final Long serialVersionUID = -2550185165626007488L;
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private static final String AUTHORITIES_KEY = "authorities";

    @Value("${jpmarget.app.jwtSecret}")
    private String jwtScret;

    @Value("${jpmarget.app.jwtSecret}")
    private byte[] jwtKey;

    @Value("${jpmarget.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    // Unused
//    public String generateJwtToken(User user) {
//
//        return JWT.create()
//                .withSubject(user.getEmail())
//                .withExpiresAt(new Date(System.currentTimeMillis()+ jwtExpirationMs))
//                .withClaim("name", user.getName())
//                .sign(Algorithm.HMAC512(jwtScret));
//    }
    public String generateJwtToken(Authentication authentication) {
        return generateJwtToken(authentication, false);
    }

    public String generateJwtToken(Authentication authentication, boolean rememberMe) {

    String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

    Long now = (Long)(new Date()).getTime();
    Date validity;
    if (rememberMe) {
        validity = new Date(now + 10 * jwtExpirationMs);
    } else {
        validity = new Date(now + jwtExpirationMs);
    }

    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setId(customUserDetails.getId().toString())
                .setSubject(customUserDetails.getEmail())
                .setIssuedAt(new Date())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS512, jwtKey)
                .setExpiration(validity)
                .compact();
    }

    public String generateRefreshToken(String userEmail) {
        return Jwts.builder().setSubject(userEmail).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtScret).compact();
    }

    public String getUserNameFromJwtToken(String token) {
        System.out.println("JWT token UserName: " + Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token).getBody().getSubject());
        return  Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJwt(token).getBody().getSubject();
    }

    public String getPasswordFromJwtToken(String token) {
        System.out.println("JWT token Password: " + Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token).getBody().getSubject());
        return  Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJwt(token).getBody().getSubject();
    }

    public Authentication getAuthenticationFromJwtToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtKey)
                .build()
                .parseClaimsJwt(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails userDetails = User.builder()
                .username(getUserNameFromJwtToken(token))
                .authorities(authorities)
                .password(getPasswordFromJwtToken(token))
                .build();

        // User principal = userRepository.findByEmail(authorities.toString()).orElse(null);

        return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
    }

    public String getUserEmailFromJwtToken(String token) {
        System.out.println("JWT token Email: " + Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token).getBody().getSubject());
        return  Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validateJwtToken(String authToken) {
        System.out.println("Trying validate token " + authToken);
        try{
            Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            logger.info("JWT token compact of handler are invalid.");
            logger.trace("JWT token compact of handler are invalid trace: ", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            logger.info("JWT token compact of handler are invalid.");
            logger.trace("JWT token compact of handler are invalid trace: ", e);
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            logger.info("JWT token compact of handler are invalid.");
            logger.trace("JWT token compact of handler are invalid trace: ", e);
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            logger.info("JWT token compact of handler are invalid.");
            logger.trace("JWT token compact of handler are invalid trace: ", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            logger.info("JWT token compact of handler are invalid.");
            logger.trace("JWT token compact of handler are invalid trace: ", e);
        }

        return false;
    }
}
