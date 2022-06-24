package com.jpmarket.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jpmarket.config.auth.dto.CustomUserDetails;
import com.jpmarket.domain.user.User;
import com.jpmarket.domain.user.UserRepository;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private static final String AUTHORITIES_KEY = "authorities";

    @Autowired
    UserRepository userRepository;

    @Value("${jpmarget.app.jwtSecret}")
    private String jwtScret;

    @Value("${jpmarget.app.jwtSecret}")
    private byte[] jwtKey;

    @Value("${jpmarget.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(User user) {

        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis()+ jwtExpirationMs))
                .withClaim("name", user.getName())
                .sign(Algorithm.HMAC512(jwtScret));
    }
        public String generateJwtToken(Authentication authentication) {
            return generateJwtToken(authentication, false);
        }

        public String generateJwtToken(Authentication authentication, boolean rememberMe) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + 10 * jwtExpirationMs);
        } else {
            validity = new Date(now + jwtExpirationMs);
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//        return JWT.create()
//                .withSubject(Long.toString(customUserDetails.getId()))
//                .withSubject(customUserDetails.getEmail())
//                .withIssuedAt(new Date())
//                .withExpiresAt(validity)
//                .withClaim(AUTHORITIES_KEY, authorities)
//                .sign(Algorithm.HMAC512(jwtKey));
            return Jwts.builder()
                    .setId(customUserDetails.getId().toString())
                    .setSubject(customUserDetails.getEmail())
                    .setIssuedAt(new Date())
                    .claim(AUTHORITIES_KEY, authorities)
                    .signWith(SignatureAlgorithm.HS512, jwtKey)
                    .setExpiration(validity)
                    .compact();
    }
    public String getUserNameFromJwtToken(String token) {
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

        User principal = userRepository.findByEmail(authorities.toString()).orElse(null);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
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
