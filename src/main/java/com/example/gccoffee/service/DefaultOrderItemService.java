package com.example.gccoffee.service;

import com.example.gccoffee.model.OrderItem;
import com.example.gccoffee.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultOrderItemService implements OrderItemService {


    private final OrderItemRepository orderItemRepository;

    public DefaultOrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public List<OrderItem> findOrderItemsByOrderId(String orderId) {
        return orderItemRepository.findOrderItemsByOrderId(orderId);
    }
}
