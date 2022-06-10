package com.jpmarket.web.chatroomDto;

import com.jpmarket.domain.chatroom.ChatRoom;
import lombok.Getter;

@Getter
public class ChatRoomListRequestDto {
    private Long id;

    public ChatRoomListRequestDto(ChatRoom entity) {
        this.id = entity.getId();
    }
}
