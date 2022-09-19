package com.jpmarket.web;

import com.jpmarket.config.auth.LoginUser;
import com.jpmarket.config.auth.dto.CustomUserDetails;
import com.jpmarket.config.auth.requestAndResponse.JwtAuthResponse;
import com.jpmarket.config.auth.requestAndResponse.LoginRequest;
import com.jpmarket.domain.user.Role;
import com.jpmarket.domain.verificationToken.VerificationToken;
import com.jpmarket.service.UserService;
import com.jpmarket.web.userDto.GetCurrentUserDto;
import com.jpmarket.web.userDto.SignUpRequest;
import com.jpmarket.config.jwt.JwtUtils;
import com.jpmarket.domain.user.User;
import com.jpmarket.domain.user.UserRepository;
import io.jsonwebtoken.io.IOException;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintValidator;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Pattern emailPattern = Pattern.compile("\\w+@\\w+\\.\\w+(\\.\\w+)?");
    private final Pattern passwordPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$");
    @Autowired(required = true)
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserService userService;

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

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public GetCurrentUserDto getCurrentUser(@LoginUser CustomUserDetails CustomUserDetails) {
        logger.debug("REST request to get user : {}", CustomUserDetails.getEmail());
        User user = userService.findById(CustomUserDetails.getId()).toEntity();

        return GetCurrentUserDto.builder()
                .email(user.getEmail())
                .build();
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        logger.debug("REST request to signup : {}", signUpRequest.getEmail());
        System.out.println("REST request to signup : " + signUpRequest.getEmail());
        logger.info("emil exiest: " + userService.checkExistByEmail(signUpRequest.getEmail()));
        if(userService.checkExistByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Email address already in use.");
        }
        else if(signUpRequest.getName().isEmpty())
        {
            throw new RuntimeException(("UserName empty"));
        }
        else{
            Matcher emailMatcher = emailPattern.matcher(signUpRequest.getEmail());
            Matcher passwordMatcher = passwordPattern.matcher(signUpRequest.getPassword());
            if(!emailMatcher.matches() && !passwordMatcher.matches() )
                throw new RuntimeException("Email or Password is wrong format");
        }
        User result = userService.processNewAccount(signUpRequest);

        return new ResponseEntity<User>(result, HttpStatus.CREATED);
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam("token") String token)
    {
        VerificationToken verificationToken = userService.getVerificationToken(token);
        logger.info("email token: " + token);
        if (verificationToken == null) {
            return "auth.message.invalidToken";
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if((verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            return "badUser time exceeded";
        }
        logger.info("email confirmed: " + user.getEmail());
        userService.updateEmailVerified(user);

        return "email verified";
    }

}

