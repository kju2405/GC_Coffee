package com.example.gccoffee.repository;

import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.OrderItem;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

@Repository
public class OrderItemJdbcRepository implements OrderItemRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderItemJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<OrderItem> findOrderItemsByOrderId(String orderId) {
        return jdbcTemplate.query("select p.product_name, p.product_id, oi.category,oi.price,oi.quantity " +
            "from orders o join order_items oi on o.order_id = oi.order_id " +
            "join products p on oi.product_id = p.product_id " +
            "where o.order_id=:orderId", Collections.singletonMap("orderId", orderId), orderItemRowMapper());
    }

    private static RowMapper<OrderItem> orderItemRowMapper() {
        return ((rs, rowNum) -> {
            String productName = rs.getString("product_name");
            String productId = rs.getString("product_id");
            Category category = Category.valueOf(rs.getString("category"));
            long price = rs.getLong("price");
            int quantity = rs.getInt("quantity");
            return new OrderItem(productName,productId, category, price, quantity);
        });
    }
}
