package com.jpmarket.web.userDto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class GetCurrentUserDto {

    private String name;

    private String email;

    @Builder
    GetCurrentUserDto(Long id, String name, String email){
        this.name = name;
        this.email = email;
    }

}
