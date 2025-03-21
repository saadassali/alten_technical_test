package com.altev.ecommerce.controller;

import com.altev.ecommerce.dto.ProductDTO;
import com.altev.ecommerce.entity.Product;
import com.altev.ecommerce.service.impl.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping()
    public ResponseEntity<String> addToCart(@RequestParam Long productId) {
        cartService.addToCart(productId);
        return ResponseEntity.ok("Product added to cart successfully.");
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long productId) {
        cartService.removeFromCart(productId);
        return ResponseEntity.ok("Product removed from cart successfully.");
    }
    @GetMapping()
    public List<ProductDTO> getProductsFromUserCart() {
        return cartService.getProductsFromUserCart();
    }
}
