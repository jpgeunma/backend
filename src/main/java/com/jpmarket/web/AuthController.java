package com.jpmarket.web;

import com.jpmarket.config.auth.LoginUser;
import com.jpmarket.config.auth.dto.CustomUserDetails;
import com.jpmarket.config.auth.requestAndResponse.JwtAuthResponse;
import com.jpmarket.config.auth.requestAndResponse.LoginRequest;
import com.jpmarket.config.auth.requestAndResponse.SignUpRequest;
import com.jpmarket.config.jwt.JwtUtils;
import com.jpmarket.domain.user.User;
import com.jpmarket.domain.user.UserRepository;
import io.jsonwebtoken.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = true)
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserRepository userRepository;


    private final String AUTHORIZATION_HEADER = "Authorization";

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest request
            , @RequestParam(value = "rememberMe", defaultValue = "false", required = false) boolean rememberMe
            , HttpServletResponse response) throws AuthenticationException {
        logger.debug("REST request to authenticate : {}", request.getEmail());
        System.out.println("REST request to authenticate : " +  request.getEmail());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = "Bearer " + jwtUtil.generateJwtToken(authentication, rememberMe);
            response.addHeader(AUTHORIZATION_HEADER, jwt);
            return ResponseEntity.ok(new JwtAuthResponse(jwt));
        } catch (AuthenticationException ae) {
            logger.trace("Authentication exception trace: {}", ae);
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",
                    ae.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@LoginUser CustomUserDetails CustomUserDetails) {
        logger.debug("REST request to get user : {}", CustomUserDetails.getEmail());
        return userRepository.findById(CustomUserDetails.getId())
                .orElseThrow(() -> new IOException("not valid Current User"));
    }
    // TODO not implemented yet
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        logger.debug("REST request to signup : {}", signUpRequest.getEmail());
        if(userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email address already in use.");
        }

        User user = User.builder()
                        .name(signUpRequest.getName())
                        .email(signUpRequest.getEmail())
                        .picture("")
                        .build();
        user.setPassword(signUpRequest.getPassword());
        User result = userRepository.save(user);

        return new ResponseEntity<User>(result, HttpStatus.CREATED);
    }
}
