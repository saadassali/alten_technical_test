package com.altev.ecommerce.unittests;

import com.altev.ecommerce.dao.ProductRepository;
import com.altev.ecommerce.dao.UserRepository;
import com.altev.ecommerce.dto.ProductDTO;
import com.altev.ecommerce.entity.Product;
import com.altev.ecommerce.exception.ProductNotFoundException;
import com.altev.ecommerce.mappers.ProductMapper;
import com.altev.ecommerce.service.impl.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void getAllProducts_shouldReturnProductDTOs() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductDTO> products = productService.getAllProducts();

        assertEquals(1, products.size());
        assertEquals("Test Product", products.get(0).getName());
    }

    @Test
    void getProductById_whenProductExists_shouldReturnProductDTO() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Sample");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductDTO result = productService.getProductById(1L);
        assertEquals("Sample", result.getName());
    }

    @Test
    void getProductById_whenProductDoesNotExist_shouldThrowException() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(99L));
    }

    @Test
    void saveProduct_shouldPersistAndReturnProductDTO() {
        ProductDTO dto = new ProductDTO();
        dto.setName("New");

        Product entity = ProductMapper.dtoToEntity(dto);
        entity.setId(1L);

        when(productRepository.save(any(Product.class))).thenReturn(entity);

        ProductDTO result = productService.saveProduct(dto);
        assertEquals("New", result.getName());
    }

    @Test
    void updateProduct_whenProductNotFound_shouldThrowException() {
        ProductDTO dto = new ProductDTO();
        dto.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(dto));
    }
}
