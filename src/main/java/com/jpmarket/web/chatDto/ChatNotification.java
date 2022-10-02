package com.jpmarket.web.chatDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class ChatNotification {
    private Long id;
    private Long senderId;
    private String senderName;

    @Builder
    ChatNotification(Long id, Long senderId, String senderName){
        this.id = id;
        this.senderId = senderId;
        this.senderName = senderName;
    }
}
