package com.example.gccoffee.controller;

import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.Order;
import com.example.gccoffee.model.OrderStatus;
import com.example.gccoffee.model.Phone;
import com.example.gccoffee.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/products/orders")
    public String ordersPage(Model model) {
        List<Order> allOrders = orderService.findAllOrders();
        model.addAttribute("orders", allOrders);
        return "product-order-list";
    }

    @GetMapping("/products/orders/{orderId}/edit")
    public String orderEditPage(@PathVariable String orderId, Model model) {
        Order order = orderService.findOrdersById(orderId);
        model.addAttribute("order", order);
        return "edit-product-order";
    }

    @PostMapping("/products/orders/{orderId}/edit")
    public String orderEdit(@RequestParam String orderId,
                            @RequestParam Email email,
                            @RequestParam Phone phoneNum,
                            @RequestParam String bellNumber,
                            @RequestParam OrderStatus orderStatus,
                            @RequestParam LocalDateTime createdAt) {
        Order order = new Order(orderId, email, phoneNum, bellNumber, null, orderStatus, createdAt, LocalDateTime.now());
        orderService.updateOrder(order);
        return "redirect:/products/orders";
    }
}
