package com.jpmarket.web.chatroomDto;

import com.jpmarket.domain.chatroom.ChatRoom;
import lombok.Getter;

@Getter
public class ChatRoomResponseDto {
    private Long id;

    public ChatRoomResponseDto(ChatRoom entity) {
        this.id = entity.getId();
    }
}
