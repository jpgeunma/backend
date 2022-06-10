package com.jpmarket.web.chatroomDto.messagesDto;

import com.jpmarket.domain.chatroom.messages.Messages;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageRequestDto {
    private Long roomId;

    private String sender;

    private String message;

    @Builder
    public MessageRequestDto(Long roomId, String sender, String message)
    {
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
    }

    public Messages toEntity() {

        return Messages.builder()
                .roomId(roomId)
                .sender(sender)
                .message(message)
                .build();
    }
}
