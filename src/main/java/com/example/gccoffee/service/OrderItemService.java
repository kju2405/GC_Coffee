package com.example.gccoffee.service;

import com.example.gccoffee.model.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> findOrderItemsByOrderId(String orderId);
}
