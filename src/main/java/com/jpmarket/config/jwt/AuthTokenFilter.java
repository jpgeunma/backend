package com.jpmarket.config.jwt;

import com.jpmarket.config.auth.CustomUserDetailsService;
import com.jpmarket.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    // TODO 서비스 시작 할 때 추가
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = getJwtFromRequest(request);
            String userEmail;
            UserDetails user;
            logger.debug("request from Jwt Token {}", jwt);
            if ( StringUtils.hasText(jwt) && jwtUtils.validateJwtToken(jwt)) {
                userEmail = jwtUtils.getUserEmailFromJwtToken(jwt);
                user = (UserDetails)userDetailsService.loadUserByUsername(userEmail);
                logger.debug("User name from Jwt Token {}", userEmail);

                UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) getAuthorities(user);
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
        }
        System.out.println("filterChain.doFilter");

        // 전처리
        filterChain.doFilter(request, response);
        // 후처리
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public Authentication getAuthorities(UserDetails user) {
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

}
