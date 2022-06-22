package com.jpmarket.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmarket.config.auth.dto.OAuthAttributes;
import com.jpmarket.config.auth.dto.SessionUser;
import com.jpmarket.config.jwt.CookieUtils;
import com.jpmarket.config.jwt.JwtUtils;
import com.jpmarket.domain.user.User;
import com.jpmarket.domain.user.UserRepository;
import jdk.nashorn.internal.parser.JSONParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.jpmarket.config.auth.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

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

        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if(redirectUri.isPresent()) {
            throw new ServletException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication Status");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        String token = jwtUtils.generateJwtToken(authentication);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        HttpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestResponse(request, response);
    }
}
