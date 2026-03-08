package com.AI.order_support_chatbot.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="chat_messages")
@Data
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 1000)
    private String userMessage;

    @Column(nullable = false, length = 2000)
    private String aiResponse;

    @Column(name = "created_at")
    private LocalDateTime createdAt= LocalDateTime.now();
}
