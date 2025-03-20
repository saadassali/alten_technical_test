package com.altev.ecommerce.controller;

import com.altev.ecommerce.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToWishlist(@RequestParam Long productId) {
        wishlistService.addToWishlist(productId);
        return ResponseEntity.ok("Product added to wishlist successfully.");
    }
}
