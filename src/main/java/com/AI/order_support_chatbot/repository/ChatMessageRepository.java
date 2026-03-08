package com.AI.order_support_chatbot.repository;

import com.AI.order_support_chatbot.model.ChatMessage;
import com.AI.order_support_chatbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByUserOrderByCreatedAtDesc(User user);
}
