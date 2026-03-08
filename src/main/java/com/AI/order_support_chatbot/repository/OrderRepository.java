package com.AI.order_support_chatbot.repository;

import com.AI.order_support_chatbot.model.Order;
import com.AI.order_support_chatbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    Optional<Order> findByOrderNumber(String orderNumber);
}
