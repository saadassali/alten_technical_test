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
        product.setName(dto.name());
        product.setCode(dto.code());
        product.setQuantity(dto.quantity());
        product.setDescription(dto.description());
        product.setCategory(dto.category());
        product.setPrice(dto.price());
        product.setQuantity(dto.quantity());
        product.setInternalReference(dto.internalReference());
        product.setShellId(dto.shellId());
        product.setInventoryStatus(dto.inventoryStatus());
        product.setRating(dto.rating());
        product.setImage(dto.image());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        return product;
    }

    public static ProductDTO entityToDto(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO dto = new ProductDTO(product.getId(),product.getCode(), product.getName(), product.getDescription(), product.getImage(), product.getCategory()
        , product.getPrice(), product.getQuantity(), product.getInternalReference(), product.getShellId(), product.getInventoryStatus(), product.getRating()
        );
        return dto;
    }
}
