package com.example.gccoffee.model;

import java.util.UUID;

public record OrderItem(String productName, String productId, Category category, long price, int quantity) {

}
