package com.example.gccoffee.repository;

import com.example.gccoffee.model.Order;

import java.util.List;

public interface OrderRepository {
    Order insert(Order order);

    void deleteAll();

    List<Order> findAllOrders();

    Order findOrdersById(String orderId);

    Order update(Order order);
}
