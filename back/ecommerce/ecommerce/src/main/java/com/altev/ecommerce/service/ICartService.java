package com.altev.ecommerce.service;

import com.altev.ecommerce.dto.ProductDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ICartService {
    void addToCart(Long productId);

    @Transactional
    void removeFromCart(Long productId);

    List<ProductDTO> getProductsFromUserCart();
}
