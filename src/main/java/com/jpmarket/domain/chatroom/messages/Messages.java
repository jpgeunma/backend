package com.jpmarket.domain.chatroom.messages;

import com.jpmarket.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Messages extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long roomId;

    @Column
    private String sender;

    @Column
    private String receiver;

    @Column
    private String message;

    private MessagesType type;

    @Builder
    Messages (Long roomId, String sender, String receiver, String message)
    {
        this.roomId = roomId;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }
}
