package com.AI.order_support_chatbot.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name= "user_id, nullable=false")
    private User user;

    @Column(name = "order_number", nullable= false, unique = true)
    private String orderNumber;

    @Column(nullable = false)
    private String status; // order placed, shipped, delivered, cancelled

    @Column(nullable = false)
    private String product;

    @Column(nullable = false)
    private Double amount;

    @Column(name="order_date")
    private LocalDateTime orderDate= LocalDateTime.now();
}

