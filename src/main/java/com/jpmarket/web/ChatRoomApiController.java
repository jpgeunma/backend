package com.jpmarket.web;

import com.jpmarket.domain.chatroom.messages.Messages;
import com.jpmarket.service.ChatService;
import com.jpmarket.web.chatroomDto.ChatRoomCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ChatRoomApiController {
    private final ChatService chatService;

    @GetMapping("/api/v1/room")
    public Long save(@RequestBody ChatRoomCreateDto requestDto)
    {
        return chatService.save(requestDto);
    }

//    @GetMapping("/api/v1/room/{id}")
//    public Long enter(@PathVariable Long id, @RequestBody )

    @DeleteMapping("/api/v1/room/{id}")
    public Long delete(@PathVariable Long id) {
        chatService.delete(id);
        return id;
    }




}
