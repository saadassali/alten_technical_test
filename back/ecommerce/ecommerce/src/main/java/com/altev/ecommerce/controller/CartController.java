package com.altev.ecommerce.controller;

import com.altev.ecommerce.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestParam Long productId) {
        cartService.addToCart(productId);
        return ResponseEntity.ok("Product added to cart successfully.");
    }
}
