package com.jpmarket.web.messageDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponseDto {
    private Long id;
    private String sender;
    private String receiver;
    private String content;

    @Builder
    public MessageResponseDto(Long id, String sender, String receiver, String content) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }
}
