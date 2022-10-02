package com.jpmarket.domain.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Long countBySenderIdAndReceiverIdAndStatus(Long senderId, Long receiverId, MessageStatus status);

    List<Message> findAllBySenderIdAndReceiverId(Long senderId, Long receiverId);
    List<Message> findByChatId(Long chatId);
}
