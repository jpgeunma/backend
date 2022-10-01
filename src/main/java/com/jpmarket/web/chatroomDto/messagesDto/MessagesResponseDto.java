package com.jpmarket.web.chatroomDto.messagesDto;

import com.jpmarket.domain.chatroom.message.Message;
import lombok.Getter;

@Getter
public class MessagesResponseDto {

    private Long id;

    private String sender;

    private String message;

//    public MessagesResponseDto(Message entity) {
//        this.id = entity.getRoomId();
//        this.sender = entity.getSender();
//        this.message = entity.getContent();
//    }
}
