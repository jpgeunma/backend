package com.jpmarket.config.jwt;

import com.jpmarket.domain.user.User;
import com.jpmarket.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = parseJwt(request);
        try{
            System.out.println("Filter jwt " + jwt);
            System.out.println("validation: " + jwtUtils.validateJwtToken(jwt));

            if ( jwt.equals("") && jwtUtils.validateJwtToken(jwt)) {
                String userName = jwtUtils.getUserNameFromJwtToken(jwt);
                System.out.println("User name from Jwt Token " + userName);
                UserDetails user = (UserDetails) userRepository.findByName(userName)
                        .orElseThrow(()->new io.jsonwebtoken.io.IOException("no such user name"));

                UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) getAuthentication(user);
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            System.out.println("Cannot set user authentication: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(ServletRequest request) {
        String headerAuth = ((HttpServletRequest)request).getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }

    public Authentication getAuthentication(UserDetails user) {
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }
}
