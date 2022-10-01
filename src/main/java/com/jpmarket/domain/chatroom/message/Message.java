package com.jpmarket.domain.chatroom.message;

import com.jpmarket.domain.BaseTimeEntity;
import com.jpmarket.domain.chatroom.ChatRoom;
import com.jpmarket.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User sender;
    @Column
    private String content;

    private MessagesType type;

    @Builder
    Message(String content, ChatRoom chatRoom, User sender)
    {
        this.content = content;
        this.chatRoom = chatRoom;
        this.sender = sender;
    }
}
