package com.jpmarket.domain.chatroom;

import com.jpmarket.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long sellerId;
    @Column(nullable = false)
    private Long buyerId;

    @Column
    private String roomName;

    @Builder
    public ChatRoom(Long sellerId, Long buyerId)
    {
        this.sellerId = sellerId;
        this.buyerId = buyerId;
    }

}
