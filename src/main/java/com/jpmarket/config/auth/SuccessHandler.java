package com.jpmarket.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmarket.config.auth.dto.OAuthAttributes;
import com.jpmarket.config.auth.dto.SessionUser;
import com.jpmarket.config.jwt.JwtUtils;
import com.jpmarket.domain.user.User;
import com.jpmarket.domain.user.UserRepository;
import jdk.nashorn.internal.parser.JSONParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler  {

    @Autowired
    private JwtUtils jwtUtils;

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        System.out.println("Oauth2User "+oAuth2User.getAttributes());
        System.out.println("OAuth2User name "+oAuth2User.getAttributes().get("name"));
        User user = userRepository.findByName(oAuth2User.getAttributes().get("name").toString())
                .orElseThrow(()->new io.jsonwebtoken.io.IOException("no such user name"));
        String token = jwtUtils.generateJwtToken(user);

        System.out.println("token :" + token);

        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/login/redirect")
                .queryParam("token", token)
                .queryParam("email", user.getEmail())
                .queryParam("name", user.getName())
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
