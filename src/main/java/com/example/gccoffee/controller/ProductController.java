package com.example.gccoffee.controller;

import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;
import com.example.gccoffee.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String productsPage(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product-list";
    }

    @GetMapping("/products/{productId}/edit")
    public String productEditPage(@PathVariable String productId, Model model) {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        return "edit-product";
    }

    @PostMapping("/products/{productId}/edit")
    public String productEdit(@RequestParam String productId,
                              @RequestParam String productName,
                              @RequestParam Category category,
                              @RequestParam long price,
                              @RequestParam String description,
                              @RequestParam LocalDateTime createdAt) {
        Product product = new Product(productId, productName, category, price, description, createdAt, LocalDateTime.now());
        productService.updateProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/new-product")
    public String newProduct() {
        return "new-product";
    }

    @PostMapping("/products")
    public String newProduct(@ModelAttribute CreateProductRequest createProductRequest) {
        productService.createProduct(
            createProductRequest.productName(),
            createProductRequest.category(),
            createProductRequest.price(),
            createProductRequest.description());
        return "redirect:/products";
    }
    @GetMapping("/products/{productId}/delete")
    public String productDelete(@PathVariable String productId) {
        productService.deleteProduct(productId);
        return "redirect:/products";
    }
}
