package com.altev.ecommerce.service.impl;

import com.altev.ecommerce.dao.CartRepository;
import com.altev.ecommerce.dao.ProductRepository;
import com.altev.ecommerce.dao.UserRepository;
import com.altev.ecommerce.dto.ProductDTO;
import com.altev.ecommerce.entity.Cart;
import com.altev.ecommerce.entity.Product;
import com.altev.ecommerce.entity.User;
import com.altev.ecommerce.mappers.ProductMapper;
import com.altev.ecommerce.service.ICartService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
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

    @Override
    @Transactional
    public void removeFromCart(Long productId) {
        User user = getAuthenticatedUser();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (!cart.getProducts().contains(product)) {
            throw new RuntimeException("Product is not in the cart");
        }

        cart.getProducts().remove(product);
        cartRepository.save(cart);
    }

    @Override
    public List<ProductDTO> getProductsFromUserCart() {
        User user = getAuthenticatedUser();
        List<ProductDTO> products = new ArrayList<>();
        Optional<Cart> cart = cartRepository.findByUser(user);
        if(cart.isPresent() && !cart.get().getProducts().isEmpty()){
            return  cart.get().getProducts().stream().map(ProductMapper::entityToDto).collect(Collectors.toList());
        }else{
            return products;
        }

    }
}
