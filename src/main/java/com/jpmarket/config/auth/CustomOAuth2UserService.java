package com.jpmarket.config.auth;

import com.jpmarket.config.auth.dto.CustomUserDetails;
import com.jpmarket.config.auth.dto.OAuthAttributes;
import com.jpmarket.domain.user.User;
import com.jpmarket.domain.user.UserRepository;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
//public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        //OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        //OAuth2User oAuth2User = delegate.loadUser(userRequest);
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        System.out.println("oAuth2User: " + oAuth2User);
        System.out.println("registationId: " + registationId);
        System.out.println("userNameAttributeName: " + userNameAttributeName);

        OAuthAttributes attributes = OAuthAttributes.of(registationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        // httpSession.setAttribute("user", new SessionUser(user));



//        return new DefaultOAuth2User(
//                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
//                attributes.getAttributes(),
//                attributes.getNameAttributeKey());

        return CustomUserDetails.create(user, oAuth2User.getAttributes());
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@LoginUser CustomUserDetails CustomUserDetails) {
        return userRepository.findById(CustomUserDetails.getId())
                .orElseThrow(() -> new IOException("User" + HttpStatus.NOT_FOUND));
    }
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
