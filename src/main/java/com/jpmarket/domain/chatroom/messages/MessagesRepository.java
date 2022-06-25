package com.jpmarket.domain.chatroom.messages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reactor.core.publisher.Flux;

import java.util.List;

public interface MessagesRepository extends JpaRepository<Messages, Long> {

    List<Messages> findAllBySender(String sender);

    List<Messages> findAllBySenderAndReceiver(String sender, String receiver);

    void deleteBySenderAndReceiver(String sender, String receiver);
}
