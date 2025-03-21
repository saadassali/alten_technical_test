package com.altev.ecommerce.controller;

import com.altev.ecommerce.dto.ProductDTO;
import com.altev.ecommerce.entity.Product;
import com.altev.ecommerce.service.impl.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
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

    @DeleteMapping("/{productId}") // Added leading slash for consistency
    public ResponseEntity<String> removeFromCart(@PathVariable Long productId) {
        wishlistService.removeFromWishlist(productId);
        return ResponseEntity.ok("Product removed from wishlist successfully.");
    }
    @GetMapping()
    public List<ProductDTO> getProductsFromUserCart() {
        return wishlistService.getProductsFromUserWishlist();
    }
}
