package com.jpmarket.web;

import com.jpmarket.domain.chatroom.messages.Messages;
import com.jpmarket.domain.chatroom.messages.MessagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequiredArgsConstructor
public class MessagesApiController {

//    private final MessagesRepository messagesRepository;
//
//    @CrossOrigin
//    @GetMapping(value = "/api/v1/messages/sender/{sender}/receiver/{receiver}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<Messages> getMessages(@PathVariable String sender, @PathVariable String receiver) {
//        return messagesRepository.findBySenderAndReceiver(sender, receiver)
//                .subscribeOn(Schedulers.elastic());
//    }
//
//    @CrossOrigin
//    @GetMapping(value = "/api/v1/chatrooms/{roomId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<Messages> findByRoomId(@PathVariable Long roomId) {
//        return messagesRepository.findByRoomId(roomId)
//                .subscribeOn(Schedulers.elastic());
//    }

//    @CrossOrigin
//    @PostMapping("/api/v1/chatrooms/chat")
//    public Messages setMessages(@RequestBody Messages messages) {
//            return messagesRepository.save(messages);
//    }
}
