package com.jpmarket.web.userDto;

import com.jpmarket.domain.user.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long id;

    private String email;

    private String password;

    public UserResponseDto(User entity)
    {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
    }

    public User toEntity() {
        return User.builder()
                .id(id)
                .email(email)
                .build();
    }
}
