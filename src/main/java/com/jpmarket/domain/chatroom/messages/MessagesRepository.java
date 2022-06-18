package com.jpmarket.domain.chatroom.messages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reactor.core.publisher.Flux;

public interface MessagesRepository extends JpaRepository<Messages, Long> {

//    @Query("SELECT m FROM Messages m WHERE m.sender = ?1 AND m.receiver = ?2")
//    Flux<Messages> findBySenderAndReceiver(String sender, String receiver);
//
//    @Query("SELECT m FROM Messages m WHERE m.roomId = ?1")
//    Flux<Messages> findByRoomId(Long roomId);
}
