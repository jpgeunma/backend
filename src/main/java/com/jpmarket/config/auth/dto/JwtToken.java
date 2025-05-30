package com.jpmarket.config.auth.dto;

import com.jpmarket.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class JwtToken implements Serializable {

    private String accessTotken;

    private String type = "Bearer";

    private String refreshToken;

    private int expiresIn;

    private String scope;

    private int refreshTokenExpiresIn;

    private static final long serialVersionUID = -7353484588260422449L;

}
