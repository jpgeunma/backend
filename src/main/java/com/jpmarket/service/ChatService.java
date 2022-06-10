package com.jpmarket.service;

import com.jpmarket.domain.chatroom.ChatRoom;
import com.jpmarket.domain.chatroom.ChatRoomRepository;
import com.jpmarket.web.chatroomDto.ChatRoomCreateDto;
import com.jpmarket.web.chatroomDto.ChatRoomListRequestDto;
import com.jpmarket.web.chatroomDto.ChatRoomResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public Long save(ChatRoomCreateDto requestDto) {
        return chatRoomRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public void delete (Long id) {
        ChatRoom chatRoom = chatRoomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 채팅방이 없습니다. id=" + id));

        chatRoomRepository.delete(chatRoom);
    }
//
//    @Transactional
//    public boolean enter (Long id, Long userId) {
//        ChatRoom chatRoom = chatRoomRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 채팅방이 없습니다. id=" + id));
//
//        if(chatRoom.getBuyerId().equals(userId) || chatRoom.getSellerId().equals(userId))
//            throw new IllegalArgumentException("채팅창에 참가하려는 유저가 아닌듯 합니다. id=" + id);
//
//        return true;
//    }

    @Transactional
    public List<ChatRoomListRequestDto> findAllRoom() {
        return chatRoomRepository.findAll().stream()
                .map(ChatRoomListRequestDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ChatRoomResponseDto findById(Long id)
    {
        ChatRoom entity = chatRoomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 채팅방이 없습니다. id=" + id));

        return new ChatRoomResponseDto(entity);
    }



}
