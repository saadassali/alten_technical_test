package com.altev.ecommerce.service.impl;

import com.altev.ecommerce.dao.ProductRepository;
import com.altev.ecommerce.dao.UserRepository;
import com.altev.ecommerce.dto.ProductDTO;
import com.altev.ecommerce.entity.Product;
import com.altev.ecommerce.exception.ProductNotFoundException;
import com.altev.ecommerce.mappers.ProductMapper;
import com.altev.ecommerce.service.IProductService;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<ProductDTO> getProducts(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (category == null || category.isEmpty()) {
            return productRepository.findAll(pageable).map(ProductMapper::entityToDto);
        } else {
            return productRepository.findByCategory(category, pageable).map(ProductMapper::entityToDto);
        }
    }
    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(ProductMapper::entityToDto).collect(Collectors.toList());
    }


    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));

        return ProductMapper.entityToDto(product);
    }

    @Override
    public ProductDTO saveProduct(ProductDTO product) {
        return ProductMapper.entityToDto(productRepository.save(ProductMapper.dtoToEntity(product)));
    }

    @Override
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO) throws BadRequestException {
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

        return ProductMapper.entityToDto(updatedProduct);

    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
