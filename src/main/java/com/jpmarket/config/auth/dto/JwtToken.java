package com.jpmarket.config.auth.dto;

import com.jpmarket.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Data
public class JwtToken {

    private String accessTotken;

    private String type = "Bearer";

    private String refreshToken;

    private int expiresIn;

    private String scope;

    private int refreshTokenExpiresIn;
}
