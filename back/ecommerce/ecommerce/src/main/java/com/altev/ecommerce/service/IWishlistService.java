package com.altev.ecommerce.service;

import com.altev.ecommerce.dto.ProductDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IWishlistService {
    void addToWishlist(Long productId);

    @Transactional
    void removeFromWishlist(Long productId);

    List<ProductDTO> getProductsFromUserWishlist();
}
