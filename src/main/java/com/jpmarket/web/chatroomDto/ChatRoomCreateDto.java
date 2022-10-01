package com.jpmarket.web.chatroomDto;

import com.jpmarket.domain.chatroom.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomCreateDto {

    private Long sellerId;

    private Long buyerId;

    @Builder
    public ChatRoomCreateDto(Long sellerId, Long buyerId) {
        this.sellerId = sellerId;
        this.buyerId = buyerId;
    }

//    public ChatRoom toEntity() {
//        return ChatRoom.builder()
//                .sellerId(sellerId)
//                .buyerId(buyerId)
//                .build();
//    }
}
