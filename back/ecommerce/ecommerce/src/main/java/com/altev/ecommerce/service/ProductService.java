package com.altev.ecommerce.service;

import com.altev.ecommerce.dao.ProductRepository;
import com.altev.ecommerce.dao.UserRepository;
import com.altev.ecommerce.dto.ProductDTO;
import com.altev.ecommerce.entity.Product;
import com.altev.ecommerce.mappers.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(ProductDTO product) {

        return productRepository.save(ProductMapper.dtoToEntity(product));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
