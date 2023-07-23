package com.example.gccoffee.service;

import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.Order;
import com.example.gccoffee.model.OrderItem;
import com.example.gccoffee.model.Phone;

import java.util.List;

public interface OrderService {
    Order createOrder(Email email, Phone address, String postcode, List<OrderItem> orderItems);

    List<Order> findAllOrders();

    Order findOrdersById(String orderId);

    Order updateOrder(Order order);
}
