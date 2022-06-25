package com.jpmarket.service;

import com.jpmarket.domain.mail.EmailMessage;
import com.jpmarket.domain.user.Role;
import com.jpmarket.domain.user.User;
import com.jpmarket.domain.user.UserRepository;
import com.jpmarket.web.userDto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    @Transactional
    public User processNewAccount(@Valid SignUpRequest signUpRequest) {
        User newUser = saveNewAccount(signUpRequest);

        sendSignUpConfirmEmail(newUser);
        return newUser;
    }

    private User saveNewAccount(@Valid SignUpRequest signUpRequest) {
        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        User user = User.builder().email(signUpRequest.getEmail())
                .name(signUpRequest.getName())
                .role(Role.USER)
                .build();
        user.setPassword(signUpRequest.getPassword());
        return userRepository.save(user);
    }

    public void sendSignUpConfirmEmail(User newUser) {
        Context context = new Context();
        context.setVariable("link", "/check-email-token?token=" + newUser.getEmailCheckToken() +
                "&email=" + newUser.getEmail());
        context.setVariable("nickname", newUser.getName());
        context.setVariable("linkName", "이메일 인증하기");
        context.setVariable("message", "스터디올래 서비스를 사용하려면 링크를 클릭하세요.");
        String message = context.toString();

        EmailMessage emailMessage = EmailMessage.builder()
                .to(newUser.getEmail())
                .subject("jp market, account check")
                .message(message)
                .build();

        emailService.sendHtmlEmail(emailMessage);
    }

    @Transactional
    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

}
