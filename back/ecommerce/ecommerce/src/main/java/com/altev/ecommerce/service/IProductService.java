package com.altev.ecommerce.service;

import com.altev.ecommerce.dto.ProductDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IProductService {
    Page<ProductDTO> getProducts(String category, int page, int size);

    ProductDTO getProductById(Long id);

    ProductDTO saveProduct(ProductDTO product);

    ProductDTO updateProduct(@RequestBody ProductDTO productDTO) throws BadRequestException;
    List<ProductDTO> getAllProducts();

    void deleteProduct(Long id);
}
