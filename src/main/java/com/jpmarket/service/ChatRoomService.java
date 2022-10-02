package com.jpmarket.service;

import com.jpmarket.domain.chat.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public Long getChatId(Long senderId, Long receiverId, boolean createIfNotExist) {
        return chatRoomRepository
                .findBySenderIdAndReceiverId(senderId, receiverId).getChatId();
    }

}
