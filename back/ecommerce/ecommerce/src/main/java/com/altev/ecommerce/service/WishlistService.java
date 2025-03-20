package com.altev.ecommerce.service;

import com.altev.ecommerce.dao.ProductRepository;
import com.altev.ecommerce.dao.UserRepository;
import com.altev.ecommerce.dao.WishlistRepository;
import com.altev.ecommerce.entity.Product;
import com.altev.ecommerce.entity.User;
import com.altev.ecommerce.entity.Wishlist;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public WishlistService(WishlistRepository wishlistRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public void addToWishlist(Long productId) {
        User user = getAuthenticatedUser();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Wishlist wishlist = wishlistRepository.findByUser(user).orElseGet(() -> {
            Wishlist newWishlist = new Wishlist();
            newWishlist.setUser(user);
            return wishlistRepository.save(newWishlist);
        });

        if (!wishlist.getProducts().contains(product)) {
            wishlist.getProducts().add(product);
            wishlistRepository.save(wishlist);
        }
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
