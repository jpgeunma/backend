package com.jpmarket.web.messageDto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class RequestUserListDto {
    @NotNull(message = "Receiver cannot be null")
    private String receiver;
}
