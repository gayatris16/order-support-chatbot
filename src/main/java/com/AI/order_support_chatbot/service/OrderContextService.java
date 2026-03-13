package com.AI.order_support_chatbot.service;

import com.AI.order_support_chatbot.model.Order;
import com.AI.order_support_chatbot.model.User;
import com.AI.order_support_chatbot.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderContextService {
    private final OrderRepository orderRepository;

    public OrderContextService(OrderRepository orderRepository, OrderRepository orderRepository1){
        this.orderRepository = orderRepository1;
    }

    public String buildOrderContext(User user){
        List<Order> orders = orderRepository.findByUser(user);

        if(orders.isEmpty()){
            return "This user has no orders.";
        }

        StringBuilder context= new StringBuilder();
        context.append("Here are the user's orders:\n\n");

        for(Order order:orders){
            context.append("-Order #").append(order.getOrderNumber())
                    .append("| Product: ").append(order.getProduct())
                    .append(" |Status: ").append(order.getStatus())
                    .append(" | Amount: Rs.").append(order.getAmount())
                    .append(" | Date: ").append(order.getOrderDate())
                    .append("\n");
        }
        return context.toString();
    }
}
