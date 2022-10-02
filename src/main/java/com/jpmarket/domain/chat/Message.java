package com.jpmarket.domain.chat;

import com.jpmarket.domain.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Message extends BaseTimeEntity {
    @Id
    private Long id;

    private Long chatId;
    private Long senderId;
    private Long receiverId;
    private String senderName;
    private String receiverName;
    private String content;
    private MessageStatus status;
}
