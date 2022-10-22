package com.jpmarket.service;


import com.jpmarket.domain.chat.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ResourceClosedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public Message save(Message message) {
        message.setStatus(MessageStatus.RECEIVED);
        messageRepository.save(message);
        return message;
    }

    public Long countNewMessage(Long senderId, Long receiverId) {
        return messageRepository.countBySenderIdAndReceiverIdAndStatus(
                senderId, receiverId, MessageStatus.RECEIVED);
    }

    public List<Message> findChatMessages(Long userId, Long postId) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByBuyerIdAndPostId(userId, postId);
        if(!chatRoom.isPresent())
            chatRoom = chatRoomRepository.findBySellerIdAndPostId(userId, postId);
        if(!chatRoom.isPresent())
            return new ArrayList<>();

        Long chatId = chatRoom.get().getId();
        List<Message> messages = messageRepository.findByChatId(chatId);

        if(messages.size() > 0) {
            updateStatuses(userId, postId, MessageStatus.DELIVERED);
        }

        return messages;
    }

    public Message findById(Long id) {
        return messageRepository.findById(id).map(message -> {
            message.setStatus(MessageStatus.DELIVERED);
            return messageRepository.save(message);
        }).orElseThrow(() -> new ResourceClosedException("can't find message (" + id + ")"));
    }
    public void updateStatuses(Long senderId, Long receiverId, MessageStatus status) {
        List<Message> messages = messageRepository.findAllBySenderIdAndReceiverId(senderId, receiverId);
        messages.stream().peek(message -> message.setStatus(status));
        messageRepository.saveAll(messages);
    }
}
