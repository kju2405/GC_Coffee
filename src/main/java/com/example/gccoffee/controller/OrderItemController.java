package com.example.gccoffee.controller;

import com.example.gccoffee.model.OrderItem;
import com.example.gccoffee.service.OrderItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/products/orders/{orderId}")
    public String orderListPage(@PathVariable String orderId, Model model) {
        List<OrderItem> orderItemsByOrderId = orderItemService.findOrderItemsByOrderId(orderId);
        model.addAttribute("orderItemList", orderItemsByOrderId);
        return "product-orderitem-list";
    }
}
