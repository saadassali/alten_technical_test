package com.altev.ecommerce.controller;

import com.altev.ecommerce.annotations.RequireAdmin;
import com.altev.ecommerce.dto.ProductDTO;
import com.altev.ecommerce.entity.Product;
import com.altev.ecommerce.service.ProductService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    @RequireAdmin
    public Product createProduct(@RequestBody ProductDTO product) {
        return productService.saveProduct(product);
    }

    @PatchMapping // No path parameter, getting ID from the request body
    @RequireAdmin // Ensure admin access
    public Product updateProduct(@RequestBody ProductDTO productDTO) throws BadRequestException {
        return  productService.updateProduct(productDTO);
    }

    @DeleteMapping("/{id}")
    @RequireAdmin
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
