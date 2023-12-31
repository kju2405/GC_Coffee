package com.example.gccoffee.service;

import com.example.gccoffee.model.*;
import com.example.gccoffee.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultOrderService implements OrderService{
    private final OrderRepository orderRepository;

    public DefaultOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Email email, Phone address, String postcode, List<OrderItem> orderItems) {
        Order order = new Order(
            UUID.randomUUID().toString(),
            email,
            address,
            postcode,
            orderItems,
            OrderStatus.ACCEPTED,
            LocalDateTime.now(),
            LocalDateTime.now());
        return orderRepository.insert(order);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAllOrders();
    }

    @Override
    public Order findOrdersById(String orderId) {
        return orderRepository.findOrdersById(orderId);
    }

    @Override
    public Order updateOrder(Order order) {
        return orderRepository.update(order);
    }
}
