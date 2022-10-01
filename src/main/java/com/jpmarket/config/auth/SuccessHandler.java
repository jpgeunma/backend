package com.jpmarket.config.auth;

import com.jpmarket.config.auth.dto.CustomUserDetails;
import com.jpmarket.config.jwt.CookieUtils;
import com.jpmarket.config.jwt.JwtUtils;
import com.jpmarket.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.jpmarket.config.auth.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@RequiredArgsConstructor
@Slf4j
@Component
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler  {

    // after oauth2 login complete
    // we have to generate jwt token and should send to front-end
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

//        String targetUrl = determineTargetUrl(request, response, authentication);
//
//        System.out.println("SuccessHandler targetUrl"+ targetUrl);
//
//        if (response.isCommitted()) {
//            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
//            return;
//        }
//
//        clearAuthenticationAttributes(request, response);
//
//        getRedirectStrategy().sendRedirect(request, response, targetUrl);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();


        String jwt = jwtUtils.generateJwtToken(authentication);

        String url = makeRedirectUrl(jwt);
        if(response.isCommitted()) {
            logger.debug("reponse has commited already " + url);
            return;
        }
        getRedirectStrategy().sendRedirect(request, response, url);
    }

    private String makeRedirectUrl(String token) {
        return UriComponentsBuilder.fromUriString("http://localhost:3000/oauth2/redirect/"+token)
                .build().toUriString();
    }



    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
        System.out.println("determineTargetUrl redirectUri"+ redirectUri);

        if(!redirectUri.isPresent()) {
            throw new ServletException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication Status");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        String token = jwtUtils.generateJwtToken(authentication);

        System.out.println("determineTargetUrl targetUrl "+ targetUrl);
        System.out.println("determineTargetUrl token "+ token);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        HttpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestResponse(request, response);
    }
}
