package com.altev.ecommerce.integrationtests;

import com.altev.ecommerce.dao.ProductRepository;
import com.altev.ecommerce.dto.ProductDTO;
import com.altev.ecommerce.entity.Product;
import com.altev.ecommerce.service.impl.ProductService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private Product testProduct;

    @BeforeEach
    public void setup() {
        testProduct = new Product();
        testProduct.setName("Integration Test Product");
        testProduct.setPrice(50.0);
        testProduct.setQuantity(5);
        testProduct.setRating(5);
        productRepository.save(testProduct);
    }

    @Test
    public void testGetAllProducts_returnsSavedProduct() {
        List<ProductDTO> products = productService.getAllProducts();

        assertFalse(products.isEmpty());
        assertTrue(products.stream().anyMatch(p -> p.getName().equals("Integration Test Product")));
    }

    @Test
    public void testGetProductById_returnsCorrectProduct() {
        ProductDTO productDTO = productService.getProductById(testProduct.getId());

        assertNotNull(productDTO);
        assertEquals("Integration Test Product", productDTO.getName());
    }

    @Test
    public void testSaveProduct_savesNewProduct() {
        ProductDTO newProduct = new ProductDTO();
        newProduct.setName("New Product");
        newProduct.setPrice(99.9);
        newProduct.setQuantity(10);

        ProductDTO saved = productService.saveProduct(newProduct);

        assertNotNull(saved.getId());
        assertEquals("New Product", saved.getName());
    }

    @Test
    public void testUpdateProduct_updatesExistingProduct() throws Exception {
        ProductDTO productToUpdate = productService.getProductById(testProduct.getId());
        productToUpdate.setName("Updated Name");

        ProductDTO updated = productService.updateProduct(productToUpdate);

        assertEquals("Updated Name", updated.getName());
    }

    @Test
    public void testDeleteProduct_removesProduct() {
        Long id = testProduct.getId();
        productService.deleteProduct(id);

        assertFalse(productRepository.findById(id).isPresent());
    }
}
