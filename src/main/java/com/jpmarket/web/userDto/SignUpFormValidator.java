package com.jpmarket.web.userDto;

import com.jpmarket.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final UserRepository userRepository;


    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(SignUpRequest.class);
    }

    @Override
    public void validate(Object object, Errors errors) {
        // TODO email, nickname
        SignUpRequest signUpRequest = (SignUpRequest) object;
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            errors.rejectValue("email", "invalid.email", new Object[]{signUpRequest.getEmail()}, "이미 사용중인 이메일 입니다.");
        }
        if (userRepository.existsByName(signUpRequest.getName())) {
            errors.rejectValue("nickname", "invalid.nickname", new Object[]{signUpRequest.getName()}, "이미 사용중인 닉네임 입니다.");
        }
    }
}
