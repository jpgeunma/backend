package com.jpmarket.config.auth.dto;

import com.jpmarket.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    private Long id; // TODO 보안 상으로 맞는지 모르것다.

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getEmail();
        this.id = user.getId();
    }
}
