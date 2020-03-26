package com.example.websocket.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WebSocketChatMessage {
    private String type;
    private String content;
    private String sender;
}
