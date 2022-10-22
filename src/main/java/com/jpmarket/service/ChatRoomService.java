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

    public Long getChatIdBySellerId(Long senderId, Long postId) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findBySellerIdAndPostId(senderId, postId);
        if(chatRoom.isPresent())
            return chatRoom.get().getId();
        else
            return -1L;
    }
    public Long getChatIdByBuyerId(Long receiverId, Long postId) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByBuyerIdAndPostId(receiverId, postId);
        if(chatRoom.isPresent())
            return chatRoom.get().getId();
        else
            return -1L;
    }

    public ChatRoom getChatRoomById(Long id){
        return chatRoomRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("No such chatroom id=" + id));
        // TODO add DTO model
    }

    public ChatRoom createChat(Long sellerId, Long buyerId, Long postId) {
        ChatRoom chatRoom = ChatRoom.builder()
                .sellerId(sellerId)
                .buyerId(buyerId)
                .postId(postId)
                .build();
        return chatRoomRepository.save(chatRoom);

    }

}
