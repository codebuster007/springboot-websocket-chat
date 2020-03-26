package com.example.websocket.controller;

import com.example.websocket.model.WebSocketChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class WebSocketChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/chatapp")
    public WebSocketChatMessage sendMessage(@Payload WebSocketChatMessage webSocketChatMessage){
        return webSocketChatMessage;
    }

    @MessageMapping("/chat.newUser")
    @SendTo("/topic/chatapp")
    public WebSocketChatMessage newUser(@Payload WebSocketChatMessage webSocketChatMessage,
                                         SimpMessageHeaderAccessor headerAccessor){
        Objects.requireNonNull(headerAccessor.getSessionAttributes())
                .put("username", webSocketChatMessage.getSender());
        return webSocketChatMessage;
    }

    @MessageMapping("/chat.Leave")
    @SendTo("/topic/chatapp")
    public WebSocketChatMessage leaveChat(@Payload WebSocketChatMessage chatMessage){
        return chatMessage;
    }
}
