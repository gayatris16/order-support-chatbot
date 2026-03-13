package com.AI.order_support_chatbot;

import com.AI.order_support_chatbot.model.Order;
import com.AI.order_support_chatbot.model.User;
import com.AI.order_support_chatbot.repository.OrderRepository;
import com.AI.order_support_chatbot.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public DataLoader(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //create test user
        // Create test user
        User user = new User();
        user.setUsername("gayatri");
        //user.setPassword(new BCryptPasswordEncoder().encode("password123"));
        user.setPassword("password123");
        user.setEmail("gayatri@test.com");
        userRepository.save(user);

        //create test orders
        Order order1=new Order();
        order1.setUser(user);
        order1.setOrderNumber("ORD-001");
        order1.setProduct("iPhone 15");
        order1.setStatus("DELIVERED");
        order1.setAmount(79999.0);
        orderRepository.save(order1);

        Order order3 = new Order();
        order3.setUser(user);
        order3.setOrderNumber("ORD-003");
        order3.setProduct("AirPods Pro");
        order3.setStatus("PLACED");
        order3.setAmount(24999.0);
        orderRepository.save(order3);

        System.out.println("✅ Test data loaded — user: gayatri, 3 orders created");

    }
}
