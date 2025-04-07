package com.altev.ecommerce.service.impl;

import com.altev.ecommerce.dao.ProductRepository;
import com.altev.ecommerce.dao.UserRepository;
import com.altev.ecommerce.dto.ProductDTO;
import com.altev.ecommerce.entity.Product;
import com.altev.ecommerce.entity.enums.InventoryStatus;
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

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
        if (productDTO.id() == null) {
           throw new BadRequestException("Product id is required");
        }

        Optional<Product> optionalProduct = productRepository.findById(productDTO.id());

        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }

        Product existingProduct = optionalProduct.get();

        if (productDTO.code() != null) existingProduct.setCode(productDTO.code());
        if (productDTO.name() != null) existingProduct.setName(productDTO.name());
        if (productDTO.description() != null) existingProduct.setDescription(productDTO.description());
        if (productDTO.image() != null) existingProduct.setImage(productDTO.image());
        if (productDTO.category() != null) existingProduct.setCategory(productDTO.category());
        if (productDTO.price() != null) existingProduct.setPrice(productDTO.price());
        if (productDTO.quantity() != null) existingProduct.setQuantity(productDTO.quantity());
        if (productDTO.internalReference() != null)
            existingProduct.setInternalReference(productDTO.internalReference());
        if (productDTO.shellId() != null) existingProduct.setShellId(productDTO.shellId());
        if (productDTO.inventoryStatus() != null)
            existingProduct.setInventoryStatus(productDTO.inventoryStatus());
        if (productDTO.rating() != null) existingProduct.setRating(productDTO.rating());

        existingProduct.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct = productRepository.save(existingProduct);

        return ProductMapper.entityToDto(updatedProduct);

    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDTO> saveAllProduct(List<ProductDTO> products) {
        List<Product> productList = products.stream().map(ProductMapper::dtoToEntity).toList();
        return productRepository.saveAll(productList).stream().map(ProductMapper::entityToDto).toList();
    }
}
