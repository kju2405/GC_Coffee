package com.example.gccoffee.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private final String orderId;
    private final Email email;
    private Phone phoneNum;
    private String bellNumber;
    private final List<OrderItem> orderItems;
    private OrderStatus orderStatus;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Order(String orderId, Email email, Phone phoneNum, String bellNumber, List<OrderItem> orderItems, OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.orderId = orderId;
        this.email = email;
        this.phoneNum = phoneNum;
        this.bellNumber = bellNumber;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getOrderId() {
        return orderId;
    }

    public Email getEmail() {
        return email;
    }

    public Phone getPhoneNum() {
        return phoneNum;
    }

    public String getBellNumber() {
        return bellNumber;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
