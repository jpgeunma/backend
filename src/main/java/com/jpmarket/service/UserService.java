package com.jpmarket.service;

import com.jpmarket.domain.mail.EmailMessage;
import com.jpmarket.domain.user.Role;
import com.jpmarket.domain.user.User;
import com.jpmarket.domain.user.UserRepository;
import com.jpmarket.domain.verificationToken.VerificationToken;
import com.jpmarket.domain.verificationToken.VerificationTokenRepository;
import com.jpmarket.web.userDto.ChangePasswordDto;
import com.jpmarket.web.userDto.SignUpRequestDto;
import com.jpmarket.web.userDto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Transactional
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
    public User processNewAccount(SignUpRequestDto signUpRequestDto) {
        User user = userRepository.findByEmail(signUpRequestDto.getEmail()).get();
        try{
            User newUser = registerNewUserAccount(signUpRequestDto);
            sendSignUpConfirmEmail(newUser);
            return newUser;
        }catch (Exception e)
        {
            logger.info("double commit with same email address");
            return null;
        }

    }
    private User registerNewUserAccount(SignUpRequestDto signUpRequestDto) throws IllegalAccessException {
        signUpRequestDto.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        if(userRepository.findByEmail(signUpRequestDto.getEmail()).isPresent())
                throw new IllegalAccessException("There is an account with that email address");
        User user = User.builder()
                .email(signUpRequestDto.getEmail())
                .name(signUpRequestDto.getName())
                .role(Role.GUEST)
                .build();
        user.setPassword(signUpRequestDto.getPassword());
        userRepository.save(user);
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
    public void sendResetPasswordEmail(User newUser) {
        String token = UUID.randomUUID().toString();
        createVerificationToken(newUser, token);

        String recipientAddress = newUser.getEmail();
        String subject = "jp marget, account check Registration Confirmation";
        String confirmationURL = "http://localhost:8080/auth/reset?token=" + token;

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

    public void createVerificationToken(User user, String token) {
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

    public void deleteVerificationTokenByUser(User user)
    {
        tokenRepository.deleteByUser(user);
    }

    public boolean checkExistByEmail(String email) {

        if(userRepository.findByEmail(email).isPresent())
            return true;
        return false;
    }
}
