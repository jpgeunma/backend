package com.jpmarket.domain.chat;

import com.jpmarket.domain.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class ChatRoom extends BaseTimeEntity {
    @Id
    private Long id;

    private Long chatId;

    private Long senderId;

    private Long receiverId;
}
