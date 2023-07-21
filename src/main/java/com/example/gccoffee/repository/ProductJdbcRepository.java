package com.example.gccoffee.repository;

import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.gccoffee.JdbcUtils.toLocalDateTime;

@Repository
public class ProductJdbcRepository implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("select * from products", productRowMapper());
    }

    @Override
    public Product insert(Product product) {
        int update = jdbcTemplate.update("insert into products(product_id, product_name, category,price, description, created_at,updated_at) " +
                "values(:productId, :productName, :category, :price, :description, :createdAt, :updatedAt)",
            toParamMap(product));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return product;
    }

    @Override
    public Product update(Product product) {
        int update = jdbcTemplate.update("update products set " +
            "product_name=:productName," +
            "category=:category," +
            "price=:price," +
            "description=:description," +
            "created_at=:createdAt," +
            "updated_at=:updatedAt " +
            "where product_id=:productId", toParamMap(product));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return product;
    }

    @Override
    public Optional<Product> findById(String productId) {
        try {
            Product product = jdbcTemplate.queryForObject("select * from products where product_id = :productId",
                Collections.singletonMap("productId", productId), productRowMapper());
            return Optional.of(product);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> findByName(String productName) {
        try {
            Product product = jdbcTemplate.queryForObject("select * from products where product_name = :productName",
                Collections.singletonMap("productName", productName),
                productRowMapper());
            return Optional.of(product);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {

        return jdbcTemplate.query("select * from products where category = :category",
            Collections.singletonMap("category", category.toString()),
            productRowMapper());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from products", Map.of());
    }

    @Override
    public void deleteById(String productId) {
        jdbcTemplate.update("delete from products where product_id=:productId", Map.of("productId", productId));
    }

    private static RowMapper<Product> productRowMapper() {
        return ((resultSet, rowNum) -> {
            String productId = resultSet.getString("product_id");
            String productName = resultSet.getString("product_name");
            Category category = Category.valueOf(resultSet.getString("category"));
            long price = resultSet.getLong("price");
            String description = resultSet.getString("description");
            LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
            LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));
            return new Product(productId, productName, category, price, description, createdAt, updatedAt);
        });
    }

    private SqlParameterSource toParamMap(Product product) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("productId", product.getProductId())
            .addValue("productName", product.getProductName())
            .addValue("category", product.getCategory().toString())
            .addValue("price", product.getPrice())
            .addValue("description", product.getDescription())
            .addValue("createdAt", Timestamp.valueOf(product.getCreatedAt()))
            .addValue("updatedAt", Timestamp.valueOf(product.getUpdateAt()));
        return parameterSource;
    }

}
