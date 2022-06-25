package com.jpmarket.web;

import com.jpmarket.service.MessageService;
import com.jpmarket.web.messageDto.MessageDto;
import com.jpmarket.web.messageDto.MessageResponseDto;
import com.jpmarket.web.messageDto.MessageSendRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessagesApiController {

    private final MessageService messageService;
    private final Environment env;

    @GetMapping("/health_check")
    public String status() {
        return String.format(
                "It's working in Post Service"
                        + ", port(local.server.port) =" + env.getProperty("local.server.port")
                        + ", port(server.port) =" + env.getProperty("server.port")
        );
    }

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestBody MessageSendRequestDto sendRequestDto) {
        log.info("Message Service's Controller Layer :: Call send Method!");

        MessageDto messageDto = MessageDto.builder()
                .sender(sendRequestDto.getSender())
                .receiver(sendRequestDto.getReceiver())
                .content(sendRequestDto.getContent())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.send(messageDto).getReceiver());
    }

    @GetMapping("/user-list/{sender}")
    public ResponseEntity<?> getUserList(@PathVariable("sender") String sender) {

        List<MessageDto> messageDtoList = messageService.getUserList(sender);
        List<MessageResponseDto> responseDtos = new ArrayList<>();

        messageDtoList.forEach(msg -> {
            responseDtos.add(MessageResponseDto.builder()
                    .sender(msg.getSender())
                    .receiver(msg.getReceiver())
                    .build());
        });

        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    @GetMapping("/message-list")
    public ResponseEntity<?> getMessageList(@RequestParam("receiver") String receiver
                                            ,@RequestParam("sender") String sender) {

        List<MessageDto> messageDtoList = messageService.getMessageList(sender, receiver);

        List<MessageResponseDto> messageResponseDtos = new ArrayList<>();

        messageDtoList.forEach(msg->{
            messageResponseDtos.add(MessageResponseDto.builder()
                    .id(msg.getId())
                    .sender(msg.getSender())
                    .receiver(msg.getReceiver())
                    .content(msg.getContent())
                    .build());
        });

        return ResponseEntity.status(HttpStatus.OK).body(messageResponseDtos);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMessageList(@RequestBody MessageDto messageDto) {

        String message = messageService.delete(messageDto.getSender(), messageDto.getReceiver());

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }


}
