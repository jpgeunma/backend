package com.jpmarket.domain.chatroom;


import com.jpmarket.domain.chatroom.messages.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reactor.core.publisher.Flux;

import java.util.List;


public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {


}
