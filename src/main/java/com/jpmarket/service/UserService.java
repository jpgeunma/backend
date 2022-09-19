package com.jpmarket.service;

import com.jpmarket.domain.mail.EmailMessage;
import com.jpmarket.domain.user.Role;
import com.jpmarket.domain.user.User;
import com.jpmarket.domain.user.UserRepository;
import com.jpmarket.domain.verificationToken.VerificationToken;
import com.jpmarket.domain.verificationToken.VerificationTokenRepository;
import com.jpmarket.web.userDto.SignUpRequest;
import com.jpmarket.web.userDto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import javax.validation.Valid;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    private final VerificationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    Logger logger;

    // TODO
    // multiple signup reqeust with same email and password can be registered!!!!
    // need to be fix
    @Transactional
    public User processNewAccount(@Valid SignUpRequest signUpRequest) {
        User newUser = getNewUserModel(signUpRequest);
        sendSignUpConfirmEmail(newUser);
        return newUser;
    }

    private User getNewUserModel(@Valid SignUpRequest signUpRequest) {
        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        User user = User.builder()
                .email(signUpRequest.getEmail())
                .name(signUpRequest.getName())
                .role(Role.GUEST)
                .build();
        user.setPassword(signUpRequest.getPassword());
        return user;
    }

    @Transactional
    public void sendSignUpConfirmEmail(User newUser) {
        String token = UUID.randomUUID().toString();
        createVerificationToken(newUser, token);

        String recipientAddress = newUser.getEmail();
        String subject = "jp marget, account check Registration Confirmation";
        String confirmationURL = "http://localhost:8080/auth/registrationConfirm?token=" + token;

        EmailMessage emailMessage = EmailMessage.builder()
                .to(recipientAddress)
                .subject(subject)
                .message(confirmationURL)
                .build();

        emailService.sendHtmlEmail(emailMessage);
    }

    @Transactional
    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no ID: " + id + " user"));
        return new UserResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findByEmail(String email) {
        User entity = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("no email: " + email + " user"));
        return new UserResponseDto(entity);
    }

    private void createVerificationToken(User user, String token) {
        if(tokenRepository.findByEmail(user.getEmail()).isPresent())
        {
            return;
        }
        VerificationToken newToken = new VerificationToken(token, user);
        tokenRepository.save(newToken);
    }

    public VerificationToken getVerificationToken(String verificationToken) {
        return tokenRepository.findByToken(verificationToken)
                .orElseThrow(() -> new IllegalArgumentException("no such token"));
    }

    public void updateEmailVerified(User user) {
        userRepository.updateEmailVerified(user.getId(), Role.USER);
    }
    public User getUserByVerificationToken(VerificationToken verificationToken) {
        return tokenRepository.findByToken(verificationToken.getToken())
                .orElseThrow(() -> new IllegalArgumentException("no such user"))
                .getUser();
    }

    public boolean checkExistByEmail(String email) {

        if(userRepository.findByEmail(email).isPresent())
            return true;
        return false;
    }
}
