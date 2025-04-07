package com.altev.ecommerce.controller;

import com.altev.ecommerce.annotations.RequireAdmin;
import com.altev.ecommerce.dto.ProductDTO;
import com.altev.ecommerce.service.IProductService;
import com.altev.ecommerce.service.impl.ProductService;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(@RequestParam(required = false) String category,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok().body(productService.getProducts(category,page,size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.getProductById(id));
    }

    @PostMapping
    @RequireAdmin
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product) {
        return ResponseEntity.ok().body(productService.saveProduct(product));
    }

    @PostMapping("/all")
    @RequireAdmin
    public ResponseEntity<List<ProductDTO>> createAllProduct(@RequestBody List<ProductDTO> products) {
        return ResponseEntity.ok().body(productService.saveAllProduct(products));
    }

    @PatchMapping
    @RequireAdmin
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) throws BadRequestException {
        return  ResponseEntity.ok().body(productService.updateProduct(productDTO));
    }

    @DeleteMapping("/{id}")
    @RequireAdmin
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
