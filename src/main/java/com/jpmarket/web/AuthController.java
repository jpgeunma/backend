package com.jpmarket.web;

import com.jpmarket.config.auth.LoginUser;
import com.jpmarket.config.auth.dto.CustomUserDetails;
import com.jpmarket.web.userDto.UserJwtAuthResponse;
import com.jpmarket.web.userDto.UserLoginRequest;
import com.jpmarket.config.response.BaseResponse;
import com.jpmarket.config.response.BaseResponseStatus;
import com.jpmarket.domain.verificationToken.VerificationToken;
import com.jpmarket.service.UserService;
import com.jpmarket.web.userDto.*;
import com.jpmarket.config.jwt.JwtUtils;
import com.jpmarket.domain.user.User;
import net.bytebuddy.utility.RandomString;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;
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
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserLoginRequest request
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
            return ResponseEntity.ok(new UserJwtAuthResponse(jwt));
        } catch (AuthenticationException ae) {
            logger.trace("Authentication exception trace: {}", ae);
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",
                    ae.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }


    // TODO function for test
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public GetCurrentUserDto getCurrentUser(@LoginUser CustomUserDetails CustomUserDetails) {
        logger.debug("REST request to get user : {}", CustomUserDetails.getEmail());
        User user = userService.findById(CustomUserDetails.getId()).toEntity();

        return GetCurrentUserDto.builder()
                .email(user.getEmail())
                .build();
    }

    @GetMapping("/user/summaries")
    public ResponseEntity<?> getUserSummaries(@LoginUser CustomUserDetails CustomUserDetails) {
        logger.debug("REST request to get user summaries : {}", CustomUserDetails.getEmail());
        UserResponseDto user = userService.findById(1L);
        UserResponseDto user1 = userService.findById(2L);
        UserResponseDto user2 = userService.findById(3L);
        List<UserResponseDto> list = Arrays.asList(user, user1, user2);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        logger.debug("REST request to signup : {}", signUpRequestDto.getEmail());
        System.out.println("REST request to signup : " + signUpRequestDto.getEmail());
        logger.info("emil exiest: " + userService.checkExistByEmail(signUpRequestDto.getEmail()));
        if(signUpRequestDto.getName().isEmpty())
        {
            throw new RuntimeException(("UserName empty"));
        }
        else{
            Matcher emailMatcher = emailPattern.matcher(signUpRequestDto.getEmail());
            Matcher passwordMatcher = passwordPattern.matcher(signUpRequestDto.getPassword());
            if(!emailMatcher.matches() && !passwordMatcher.matches() )
                throw new RuntimeException("Email or Password is wrong format");
        }
        User result = userService.processNewAccount(signUpRequestDto);

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
        userService.deleteVerificationTokenByUser(user);
        return "email verified";
    }

    @PostMapping("/user/resetPassword")
    public String forgotPassword(@RequestParam("email") String email)
    {
        UserResponseDto user = userService.findByEmail(email);
        if(user == null) {
            return "User not found Exception";
        }
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user.toEntity(), token);
        userService.sendResetPasswordEmail(user.toEntity());

        return "email sent";
    }

    @GetMapping("/reset")
    public String resetPassword(@RequestParam("token") String token)
    {
        VerificationToken verificationToken = userService.getVerificationToken(token);
        String newPassword = RandomString.make(10);
        logger.info("email token: " + token);
        if (verificationToken == null) {
            return "auth.message.invalidToken";
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if((verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            userService.deleteVerificationTokenByUser(user);
            return "badUser time exceeded";
        }
        logger.info("email confirmed: " + user.getEmail());
        userService.updatePassword(user, newPassword);
        return "password changed: " + newPassword;
    }

    @PatchMapping("/modify/{id}")
    public BaseResponse<String> modifyUserInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                               @PathVariable("id") Long id,
                                               @RequestBody UserModifyInfoDto userInfoDto)
    {
        if(id == null)
            return new BaseResponse<>(BaseResponseStatus.EMPTY_IDX);
        if(id < 0)
            return new BaseResponse<>(BaseResponseStatus.INVALID_IDX);
        if(userInfoDto == null)
            return new BaseResponse<>(BaseResponseStatus.NOT_LOGIN);

        try{
            Long userId = customUserDetails.getId();
            if(!userId.equals(id))
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            userService.updateUser(userId, userInfoDto);
            return new BaseResponse<>("user info changed");
        }catch (Exception exception) {
            return new BaseResponse<>(exception.getMessage());
        }
    }

//    @PostMapping("/changePassword")
//    public ChangePasswordDto changePassword(@RequestBody ChangePasswordDto passwordDto)
//    {
//        return userService.updatePassword();
//    }
}

