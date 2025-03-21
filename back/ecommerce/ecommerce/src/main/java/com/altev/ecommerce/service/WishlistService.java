package com.altev.ecommerce.service;

import com.altev.ecommerce.dao.ProductRepository;
import com.altev.ecommerce.dao.UserRepository;
import com.altev.ecommerce.dao.WishlistRepository;
import com.altev.ecommerce.entity.Cart;
import com.altev.ecommerce.entity.Product;
import com.altev.ecommerce.entity.User;
import com.altev.ecommerce.entity.Wishlist;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    @Transactional
    public void removeFromWishlist(Long productId) {
        User user = getAuthenticatedUser();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Wishlist wishlist = wishlistRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("WishList not found"));

        if (!wishlist.getProducts().contains(product)) {
            throw new RuntimeException("Product is not in the WishList");
        }

        wishlist.getProducts().remove(product);
        wishlistRepository.save(wishlist);
    }
    public List<Product> getProductsFromUserWishlist() {
        User user = getAuthenticatedUser();
        List<Product> products = new ArrayList<>();
        Optional<Wishlist> wishlist = wishlistRepository.findByUser(user);
        if(wishlist.isPresent() && !wishlist.get().getProducts().isEmpty()){
            return wishlist.get().getProducts();
        }else{
            return products;
        }
    }
}
