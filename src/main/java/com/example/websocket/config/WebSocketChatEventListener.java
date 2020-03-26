package com.example.websocket.config;

import com.example.websocket.model.WebSocketChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Component
public class WebSocketChatEventListener {

    @Autowired
    private SimpMessageSendingOperations sendingOperations;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event){
        System.out.println("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor stompHeaderAccessor =StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) Objects.requireNonNull(stompHeaderAccessor.getSessionAttributes())
                .get("username");

        if(username != null){
            WebSocketChatMessage webSocketChatMessage = new WebSocketChatMessage()
                    .setType("Leave")
                    .setSender(username);

            sendingOperations.convertAndSend("/topic/public", webSocketChatMessage);
        }
    }
}
