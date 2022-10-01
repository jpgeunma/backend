package com.jpmarket.web.chatroomDto.messagesDto;

import com.jpmarket.domain.chatroom.message.Message;
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

    public Message toEntity(Long roomId, String sender, String content) {
        return Message.builder()
                .build();
    }
}
