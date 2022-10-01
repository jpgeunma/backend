package com.jpmarket.service;


import com.jpmarket.domain.chatroom.message.Message;
import com.jpmarket.domain.chatroom.message.MessageRepository;
import com.jpmarket.web.messageDto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final MessageRepository messagesRepository;

//    @Transactional
//    public Message send(MessageDto messageDto){
//        log.info("Message Service's Service Layer:: Call send Method");
//
//        Message messages = Message.builder()
//                .sender(messageDto.getSender())
//                .receiver(messageDto.getReceiver())
//                .content(messageDto.getContent())
//                .build();
//
//        return messagesRepository.save(messages);
//    }
//
//    @Transactional
//    public List<MessageDto> getUserList(String sender) {
//        log.info("Message Service's Service Layer :: Call getUserList Method!");
//
//        List<Message> messagesList = messagesRepository.findAllBySender(sender);
//        List<MessageDto> messages = new ArrayList<>();
//
//        messagesList.forEach(message -> {
//            messages.add(MessageDto.builder()
//                    .sender(String.valueOf(message.getSender()))
//                    .receiver(String.valueOf(message.getReceiver()))
//                    .build());
//        });
//        return messages;
//    }
//
//    @Transactional
//    public List<MessageDto> getMessageList(String sender, String receiver) {
//        log.info("Message Service's Service Layer :: Call getMessageList Method!");
//
//        List<Message> messagesList = messagesRepository.findAllBySenderAndReceiver(sender, receiver);
//
//        List<MessageDto> messageDtos = new ArrayList<>();
//
//        messagesList.forEach(message -> {
//            messageDtos.add(MessageDto.builder()
//                    .id(message.getId())
//                    .sender(message.getSender())
//                    .receiver(message.getReceiver())
//                    .content(message.getContent())
//                    .build());
//        });
//
//        return messageDtos;
//    }
//
//    @Transactional
//    public String delete(String sender, String reciever){
//        log.info("Message Service's Service Layer :: Call delete Method!");
//
//        messagesRepository.deleteBySenderAndReceiver(sender, reciever);
//
//        return "Successfully Delete messages";
//    }
}
