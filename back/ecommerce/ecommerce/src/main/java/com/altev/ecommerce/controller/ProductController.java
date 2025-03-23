package com.altev.ecommerce.controller;

import com.altev.ecommerce.annotations.RequireAdmin;
import com.altev.ecommerce.dto.ProductDTO;
import com.altev.ecommerce.service.IProductService;
import com.altev.ecommerce.service.impl.ProductService;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<ProductDTO> getAllProducts(@RequestParam(required = false) String category,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        return productService.getProducts(category,page,size);
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    @RequireAdmin
    public ProductDTO createProduct(@RequestBody ProductDTO product) {
        return productService.saveProduct(product);
    }

    @PatchMapping
    @RequireAdmin
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO) throws BadRequestException {
        return  productService.updateProduct(productDTO);
    }

    @DeleteMapping("/{id}")
    @RequireAdmin
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
