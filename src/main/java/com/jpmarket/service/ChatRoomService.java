package com.jpmarket.service;

import com.jpmarket.domain.chatroom.message.Message;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatRoomService {

    private static final String NO_MESSAGE_TEXT = "No message yet !";

//    List<String> users = new ArrayList<>();
//    Map<String, List<Message>> messagesForMap = new HashMap<>();
//
//    public void addUser(Long roomId, String name) {
//        if(!users.contains(name)) {
//            users.add(name);
//
//            List<Message> messagesForNewUser = new ArrayList<>();
//            users.stream().forEach(u -> {
//                List<Message> messages = messagesForMap.get(u);
//
//            });
//
//            messagesForMap.put(name, messagesForNewUser);
//        }
//    }
//
//    public void sendUserMessage(Message message) {
//        if (users.contains(message.getSender()) && users.contains(message.getReceiver())) {
//            List<Message> messagesTo = messagesForMap.get(message.getReceiver());
//            List<Message> messagesFrom = messagesForMap.get(message.getSender());
//
//            List<Message> newMessagesTo = new ArrayList<>();
//            messagesTo.stream().forEach(m -> {
//                if(m.getSender().equals(message.getSender()) && m.getContent().equals(NO_MESSAGE_TEXT)){
//                    return;
//                }
//
//                if(m.getSender().equals(m.getReceiver()) && m.getContent().equals(NO_MESSAGE_TEXT)){
//                    return;
//                }
//
//                newMessagesTo.add(m);
//            });
//
//            List<Message> newMessagesFrom = new ArrayList<>();
//            newMessagesFrom.stream().forEach(m -> {
//                if(m.getSender().equals(message.getReceiver()) && m.getContent().equals(NO_MESSAGE_TEXT)){
//                    return;
//                }
//
//                if(m.getSender().equals(m.getReceiver()) && m.getContent().equals(NO_MESSAGE_TEXT)){
//                    return;
//                }
//
//                newMessagesFrom.add(m);
//            });
//                messagesForMap.get(message.getReceiver()).clear();
//                messagesForMap.get(message.getSender()).clear();
//
//                newMessagesTo.add(message);
//                newMessagesFrom.add(message);
//
//                messagesForMap.get(message.getReceiver()).addAll(newMessagesTo);
//                messagesForMap.get(message.getSender()).addAll(newMessagesFrom);
//        }
//    }
//
//    public Flux<ServerSentEvent<List<String>>> getUsers(String name) {
//        if (name != null && !name.isEmpty()) {
//            return Flux.interval(Duration.ofSeconds(1))
//                    .map(sequence -> ServerSentEvent.<List<String>>builder().id(String.valueOf(sequence))
//                            .event("user-list-event").data(users.stream().filter(u -> !u.equals(name)).collect(Collectors.toList()))
//                            .build());
//        }
//
//        return Flux.interval(Duration.ofSeconds(1)).map(sequence -> ServerSentEvent.<List<String>>builder()
//                .id(String.valueOf(sequence)).event("user-list-event").data(new ArrayList<>()).build());
//    }
//
//    public Flux<ServerSentEvent<Message>> getLastUserMessage(String name) {
//        if (name != null && !name.isEmpty()) {
//            List<Message> messages = messagesForMap.get(name);
//            return Flux.interval(Duration.ofSeconds(1))
//                    .map(sequence -> ServerSentEvent.<Message>builder().id(String.valueOf(sequence))
//                            .event("last-message-event").data(messages.get(messages.size() - 1)).build());
//        }
//
//        return Flux.interval(Duration.ofSeconds(1)).map(sequence -> ServerSentEvent.<Message>builder()
//                .id(String.valueOf(sequence)).event("last-message-event").data(null).build());
//    }
//
//
//    public Flux<ServerSentEvent<List<Message>>> getAllUserMessages(String name) {
//        if (name != null && !name.isEmpty()) {
//            List<Message> messages = messagesForMap.get(name);
//            return Flux.interval(Duration.ofSeconds(1)).map(sequence -> ServerSentEvent.<List<Message>>builder()
//                    .id(String.valueOf(sequence)).event("all-message-event").data(messages).build());
//        }
//
//        return Flux.interval(Duration.ofSeconds(1)).map(sequence -> ServerSentEvent.<List<Message>>builder()
//                .id(String.valueOf(sequence)).event("all-message-event").data(new ArrayList<>()).build());
//    }
}
