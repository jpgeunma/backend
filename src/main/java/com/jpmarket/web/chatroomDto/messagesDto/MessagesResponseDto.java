package com.jpmarket.web.chatroomDto.messagesDto;

import com.jpmarket.domain.chatroom.messages.Messages;
import lombok.Getter;

@Getter
public class MessagesResponseDto {

    private Long id;

    private String sender;

    private String message;

    public MessagesResponseDto(Messages entity) {
        this.id = entity.getRoomId();
        this.sender = entity.getSender();
        this.message = entity.getMessage();
    }
}
