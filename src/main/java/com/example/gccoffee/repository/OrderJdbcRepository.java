package com.example.gccoffee.repository;

import com.example.gccoffee.model.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class OrderJdbcRepository implements OrderRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    @Transactional
    public Order insert(Order order) {
        jdbcTemplate.update("insert into orders(order_id, email, phone_number, bell_number, order_status, created_at, updated_at)" +
            " values(:orderId, :email, :phoneNum, :bellNum, :orderStatus, :createdAt, :updatedAt)", toOrderParamMap(order));
        order.getOrderItems()
            .forEach(item -> jdbcTemplate.update("insert into order_items(order_id, product_id, category, price, quantity, created_at, updated_at)" +
                    "values(:orderId, :productId, :category, :price, :quantity, :createdAt, :updatedAt)",
                toOrderItemParamMap(item, order.getOrderId(), order.getUpdatedAt(), order.getCreatedAt())));
        return order;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from orders", Map.of());
        jdbcTemplate.update("delete from order_items", Map.of());
    }

    @Override
    public List<Order> findAllOrders() {
        List<Order> orderList = jdbcTemplate.query("select * from orders", orderRowMapper());
        return orderList;
    }

    @Override
    public Order findOrdersById(String orderId) {
        return jdbcTemplate.queryForObject("select * from orders where order_id=:orderId", Collections.singletonMap("orderId", orderId), orderRowMapper());
    }

    @Override
    public Order update(Order order) {
        int update = jdbcTemplate.update("update orders set " +
            "email = :email," +
            "phone_number = :phoneNum," +
            "bell_number = :bellNum," +
            "order_status = :orderStatus," +
            "updated_at = :updatedAt " +
            "where order_id = :orderId", toOrderParamMap(order));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return order;
    }

    private static RowMapper<Order> orderRowMapper() {
        return ((rs, rowNum) -> {
            String orderId = rs.getString("order_id");
            Email email = new Email(rs.getString("email"));
            Phone phoneNum = new Phone(rs.getString("phone_number"));
            String bellNum = rs.getString("bell_number");
            OrderStatus orderStatus = OrderStatus.valueOf(rs.getString("order_status"));
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
            return new Order(orderId, email, phoneNum, bellNum, null, orderStatus, createdAt, updatedAt);
        });
    }

    private SqlParameterSource toOrderParamMap(Order order) {
        return new MapSqlParameterSource()
            .addValue("orderId", order.getOrderId())
            .addValue("email", order.getEmail().getAddress())
            .addValue("phoneNum", order.getPhoneNum().getPhoneNumber())
            .addValue("bellNum", order.getBellNumber())
            .addValue("orderStatus", order.getOrderStatus().toString())
            .addValue("createdAt", Timestamp.valueOf(order.getCreatedAt()))
            .addValue("updatedAt", Timestamp.valueOf(order.getUpdatedAt()));
    }

    private SqlParameterSource toOrderItemParamMap(OrderItem orderItem, String orderId, LocalDateTime updatedAt, LocalDateTime createdAt) {
        return new MapSqlParameterSource()
            .addValue("orderId", orderId)
            .addValue("productId", orderItem.productId())
            .addValue("category", orderItem.category().toString())
            .addValue("price", orderItem.price())
            .addValue("quantity", orderItem.quantity())
            .addValue("createdAt", createdAt)
            .addValue("updatedAt", updatedAt);
    }
}
