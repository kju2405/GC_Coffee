package com.example.gccoffee.repository;

import com.example.gccoffee.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderJdbcRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;


    @Test
    @DisplayName("Order가 잘 저장된다.")
    void testInsert() {
        //given
        String productId1 = UUID.randomUUID().toString();
        String productId2 = UUID.randomUUID().toString();
        Product product1 = new Product(productId1, "product1", Category.COFFEE, 3000);
        Product product2 = new Product(productId2, "product2", Category.NON_COFFEE, 4000);
        productRepository.insert(product1);
        productRepository.insert(product2);
        List<OrderItem> orderItems = List.of(new OrderItem(product1.getProductName(), product1.getProductId(), product1.getCategory(), product1.getPrice(), 3),
            new OrderItem(product2.getProductName(), product2.getProductId(), product2.getCategory(), product2.getPrice(), 2));
        Order order1 = new Order(UUID.randomUUID().toString(), new Email("kju2405@gmail.com"), "경기도 수원시", "34234", orderItems, OrderStatus.ACCEPTED, LocalDateTime.now(), LocalDateTime.now());
        Order order2 = new Order(UUID.randomUUID().toString(), new Email("kju2405@gmail.com"), "경기도 수원시", "34234", orderItems, OrderStatus.ACCEPTED, LocalDateTime.now(), LocalDateTime.now());

        //when
        orderRepository.insert(order1);
        orderRepository.insert(order2);


        //then
        List<Order> allOrders = orderRepository.findAllOrders();
        assertThat(allOrders.size()).isEqualTo(2);
    }

    @Test
    void testDeleteAll() {
        //given
        String productId1 = UUID.randomUUID().toString();
        String productId2 = UUID.randomUUID().toString();
        Product product1 = new Product(productId1, "product1", Category.COFFEE, 3000);
        Product product2 = new Product(productId2, "product2", Category.NON_COFFEE, 4000);
        productRepository.insert(product1);
        productRepository.insert(product2);
        List<OrderItem> orderItems = List.of(new OrderItem(product1.getProductName(), product1.getProductId(), product1.getCategory(), product1.getPrice(), 3),
            new OrderItem(product2.getProductName(), product2.getProductId(), product2.getCategory(), product2.getPrice(), 2));
        Order order1 = new Order(UUID.randomUUID().toString(), new Email("kju2405@gmail.com"), "경기도 수원시", "34234", orderItems, OrderStatus.ACCEPTED, LocalDateTime.now(), LocalDateTime.now());
        Order order2 = new Order(UUID.randomUUID().toString(), new Email("kju2405@gmail.com"), "경기도 수원시", "34234", orderItems, OrderStatus.ACCEPTED, LocalDateTime.now(), LocalDateTime.now());
        orderRepository.insert(order1);
        orderRepository.insert(order2);
        List<Order> allOrders1 = orderRepository.findAllOrders();

        //when
        assertThat(allOrders1.size()).isEqualTo(2);
        orderRepository.deleteAll();

        //then
        List<Order> allOrders2 = orderRepository.findAllOrders();
        assertThat(allOrders2.size()).isEqualTo(0);
    }

}