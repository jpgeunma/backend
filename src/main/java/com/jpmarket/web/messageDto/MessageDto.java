package com.jpmarket.web.messageDto;

import lombok.Builder;
import lombok.Data;

@Data
public class MessageDto {
    private Long id;
    private String sender;
    private String receiver;
    private String content;

    @Builder
    public MessageDto(Long id, String sender, String receiver, String content) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }
}
