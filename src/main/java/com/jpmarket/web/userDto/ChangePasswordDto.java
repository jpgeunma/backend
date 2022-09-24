package com.jpmarket.web.userDto;

import com.jpmarket.domain.user.User;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ChangePasswordDto {

    private String origPassword;

    private String newPassword;


}
