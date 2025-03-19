package com.altev.ecommerce.mappers;

import com.altev.ecommerce.dto.ProductDTO;
import com.altev.ecommerce.entity.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class ProductMapper {

    // Convert DTO to Entity
    public static Product dtoToEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        
        // Set default values
        product.setQuantity(0);
        product.setInternalReference(null);
        product.setShellId(null);
        product.setInventoryStatus(null);
        product.setRating(0);
        product.setImage(null);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        return product;
    }

    // Convert Entity to DTO
    public static ProductDTO entityToDto(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());
        dto.setPrice(product.getPrice());

        return dto;
    }
}
