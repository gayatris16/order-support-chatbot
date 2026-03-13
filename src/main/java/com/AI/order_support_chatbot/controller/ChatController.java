package com.AI.order_support_chatbot.controller;

import com.AI.order_support_chatbot.service.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public Map<String, String> chat(@RequestBody Map<String, String> request){
        System.out.println("✅ Chat endpoint hit!");
        try{
            String username= request.get("username");
            String message= request.get("message");

            if(username== null || message== null){
                return Map.of("error", "username and message are required");
            }

            String response= chatService.chat(username, message);
            return Map.of("response", response);
        }catch (Exception e){
            System.out.println("❌ Error: " + e.getMessage());
            return Map.of("error", e.getMessage());
        }
    }
}