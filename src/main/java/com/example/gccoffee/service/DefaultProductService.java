package com.example.gccoffee.service;

import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;
import com.example.gccoffee.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultProductService implements ProductService{

    private final ProductRepository repository;

    public DefaultProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> getProductByCategory(Category category) {
        return repository.findByCategory(category);
    }

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public Product createProduct(String productName, Category category, long price) {
        Product product = new Product(UUID.randomUUID().toString(), productName, category, price);
        return repository.insert(product);
    }

    @Override
    public Product createProduct(String productName, Category category, long price, String description) {
        Product product = new Product(UUID.randomUUID().toString(), productName, category, price, description, LocalDateTime.now(), LocalDateTime.now());
        return repository.insert(product);
    }

    @Override
    public Product getProductById(String id) {
        return repository.findById(id).get();
    }

    @Override
    public Product updateProduct(Product product) {
        return repository.update(product);
    }

    @Override
    public void deleteProduct(String productId) {
        repository.deleteById(productId);
    }
}
