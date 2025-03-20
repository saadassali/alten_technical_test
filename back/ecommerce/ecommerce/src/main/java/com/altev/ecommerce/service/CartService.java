package com.altev.ecommerce.service;

import com.altev.ecommerce.dao.CartRepository;
import com.altev.ecommerce.dao.ProductRepository;
import com.altev.ecommerce.dao.UserRepository;
import com.altev.ecommerce.entity.Cart;
import com.altev.ecommerce.entity.Product;
import com.altev.ecommerce.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public void addToCart(Long productId) {
        User user = getAuthenticatedUser();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        if (!cart.getProducts().contains(product)) {
            cart.getProducts().add(product);
            cartRepository.save(cart);
        }
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
