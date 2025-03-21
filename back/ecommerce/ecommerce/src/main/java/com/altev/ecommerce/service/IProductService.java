package com.altev.ecommerce.service;

import com.altev.ecommerce.dto.ProductDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IProductService {
    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Long id);

    ProductDTO saveProduct(ProductDTO product);

    ProductDTO updateProduct(@RequestBody ProductDTO productDTO) throws BadRequestException;

    void deleteProduct(Long id);
}
