package com.jpmarket.service;

import com.jpmarket.domain.chat.ChatRoom;
import com.jpmarket.domain.chat.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public Long getChatId(Long senderId, Long receiverId) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        if(chatRoom.isPresent())
            return chatRoom.get().getId();
        else
            return -1L;
    }

    public ChatRoom createChat(Long senderId, Long receiverId) {
        ChatRoom chatRoom = ChatRoom.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
        return chatRoomRepository.save(chatRoom);

    }

}
