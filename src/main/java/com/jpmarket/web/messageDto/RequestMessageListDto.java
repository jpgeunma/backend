package com.jpmarket.web.messageDto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class RequestMessageListDto {
    @NotNull(message = "Sender cannot be null")
    private String sender;

    @NotNull(message = "Receiver cannot be null")
    private String receiver;
}
