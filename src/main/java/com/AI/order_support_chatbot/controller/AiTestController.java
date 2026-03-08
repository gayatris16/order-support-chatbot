package com.AI.order_support_chatbot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@RestController
public class AiTestController {

    private static final String URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=AIzaSyA-Uv243ry4GD7WL3zC8jrCFPZziSDakyo";

    @GetMapping("/api/test")
    public Map<String, Object> test(
            @RequestParam(defaultValue = "Say hello in one sentence") String message) {
        try {
            String body = """
            {
                "contents": [{"role": "user", "parts": [{"text": "%s"}]}]
            }
            """.formatted(message);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return Map.of("status", response.statusCode(), "response", response.body());

        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }
}