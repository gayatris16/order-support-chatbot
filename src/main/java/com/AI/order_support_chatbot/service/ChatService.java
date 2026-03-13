package com.AI.order_support_chatbot.service;

import com.AI.order_support_chatbot.model.ChatMessage;
import com.AI.order_support_chatbot.model.User;
import com.AI.order_support_chatbot.repository.ChatMessageRepository;
import com.AI.order_support_chatbot.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ChatService {
    private final OrderContextService orderContextService;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash-lite:generateContent?key=";

    public ChatService(OrderContextService orderContextService, ChatMessageRepository chatMessageRepository, UserRepository userRepository) {
        this.orderContextService = orderContextService;
        this.chatMessageRepository = chatMessageRepository;
        this.userRepository = userRepository;
    }

    public String chat(String username, String userMessage) throws Exception {

        // Step 1: Find User
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        // Step 2: Fetch order context from DB
        String orderContext = orderContextService.buildOrderContext(user);

        // Step 3: Build enriched prompt
        String prompt = """
                You are a helpful order support assistant.
                Answer the user's question based ONLY on the order data provided below.
                If the answer is not in the data, say "I don't have that information."
                %s
                
                User question: %s
                """.formatted(orderContext, userMessage);

        // Step 4: Call Gemini API
        String apiKey = "AIzaSyBbL-SVUL7kYX-PWLXEbdCQ7zsDgWcoEZ4";
        System.out.println("🔑 API Key being used: " + apiKey);

        String body = """
                {
                    "contents": [{"role": "user", "parts": [{"text": "%s"}]}]
                }
                """.formatted(prompt.replace("\"", "'").replace("\n", "\\n"));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GEMINI_URL + apiKey))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        // Step 5: Extract text from response
        String aiResponse = extractText(response.body());

        // Step 6: Save conversation to DB
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUser(user);
        chatMessage.setUserMessage(userMessage);
        chatMessage.setAiResponse(aiResponse);
        chatMessageRepository.save(chatMessage);

        return aiResponse;
    }

    private String extractText(String jsonResponse) {
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            return root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();
        } catch (Exception e) {
            return "Sorry, I could not process your request. Raw: " + jsonResponse;
        }
    }
}