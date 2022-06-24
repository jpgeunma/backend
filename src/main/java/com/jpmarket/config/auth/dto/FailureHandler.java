package com.jpmarket.config.auth.dto;

import com.jpmarket.config.auth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.jpmarket.config.jwt.CookieUtils;
import com.jpmarket.domain.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.jpmarket.config.auth.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
public class FailureHandler extends SimpleUrlAuthenticationFailureHandler  {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
//
//        System.out.println("failure " + exception.getMessage());
//
//        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/login/redirect/failure")
//                .build().toUriString();
//
//        getRedirectStrategy().sendRedirect(request, response, targetUrl);

        String targetUrl = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse(("/"));
        System.out.println("failure " + exception.getMessage());

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", exception.getLocalizedMessage())
                .build().toUriString();

        HttpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestResponse(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}