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
        product.setCode(dto.getCode());
        product.setQuantity(dto.getQuantity());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setInternalReference(dto.getInternalReference());
        product.setShellId(dto.getShellId());
        product.setInventoryStatus(dto.getInventoryStatus());
        product.setRating(dto.getRating());
        product.setImage(dto.getImage());
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

        if (product.getId() != null) dto.setId(product.getId());
        if (product.getName() != null) dto.setName(product.getName());
        if (product.getDescription() != null) dto.setDescription(product.getDescription());
        if (product.getCategory() != null) dto.setCategory(product.getCategory());
        if (product.getPrice() != null) dto.setPrice(product.getPrice());
        if (product.getQuantity() != null) dto.setQuantity(product.getQuantity());
        if (product.getInternalReference() != null) dto.setInternalReference(product.getInternalReference());
        if (product.getShellId() != null) dto.setShellId(product.getShellId());
        if (product.getInventoryStatus() != null) dto.setInventoryStatus(product.getInventoryStatus());
        if (product.getRating() != null) dto.setRating(product.getRating());
        if (product.getImage() != null) dto.setImage(product.getImage());



        return dto;
    }
}
