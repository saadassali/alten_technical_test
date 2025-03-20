package com.altev.ecommerce.service;

import com.altev.ecommerce.dao.ProductRepository;
import com.altev.ecommerce.dao.UserRepository;
import com.altev.ecommerce.dto.ProductDTO;
import com.altev.ecommerce.entity.Product;
import com.altev.ecommerce.exception.ProductNotFoundException;
import com.altev.ecommerce.mappers.ProductMapper;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
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

    public Product updateProduct(@RequestBody ProductDTO productDTO) throws BadRequestException {
        if (productDTO.getId() == null) {
           throw new BadRequestException("Product id is required");
        }

        Optional<Product> optionalProduct = productRepository.findById(productDTO.getId());

        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }

        Product existingProduct = optionalProduct.get();

        // Apply only non-null updates
        if (productDTO.getCode() != null) existingProduct.setCode(productDTO.getCode());
        if (productDTO.getName() != null) existingProduct.setName(productDTO.getName());
        if (productDTO.getDescription() != null) existingProduct.setDescription(productDTO.getDescription());
        if (productDTO.getImage() != null) existingProduct.setImage(productDTO.getImage());
        if (productDTO.getCategory() != null) existingProduct.setCategory(productDTO.getCategory());
        if (productDTO.getPrice() != null) existingProduct.setPrice(productDTO.getPrice());
        if (productDTO.getQuantity() != null) existingProduct.setQuantity(productDTO.getQuantity());
        if (productDTO.getInternalReference() != null)
            existingProduct.setInternalReference(productDTO.getInternalReference());
        if (productDTO.getShellId() != null) existingProduct.setShellId(productDTO.getShellId());
        if (productDTO.getInventoryStatus() != null)
            existingProduct.setInventoryStatus(productDTO.getInventoryStatus());
        if (productDTO.getRating() != null) existingProduct.setRating(productDTO.getRating());

        existingProduct.setUpdatedAt(LocalDateTime.now()); // Update the timestamp

        Product updatedProduct = productRepository.save(existingProduct); // Save updated product

        return updatedProduct;

    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
