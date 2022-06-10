package com.jpmarket.domain.chatroom.messages;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Messages {

    private Long roomId;

    private String sender;

    private String message;

    @Builder
    public Messages(Long roomId, String sender, String message) {
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
    }
}
