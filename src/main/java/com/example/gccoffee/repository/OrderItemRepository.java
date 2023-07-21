package com.example.gccoffee.repository;

import com.example.gccoffee.model.OrderItem;

import java.util.List;

public interface OrderItemRepository {
    List<OrderItem> findOrderItemsByOrderId(String orderId);
}
