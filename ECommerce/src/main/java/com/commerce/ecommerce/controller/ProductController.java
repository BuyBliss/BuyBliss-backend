package com.commerce.ecommerce.controller;


import com.commerce.ecommerce.model.dto.ProductDTO;
import com.commerce.ecommerce.model.entity.Product;
import com.commerce.ecommerce.model.response.ProductSearchResponse;
import com.commerce.ecommerce.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestParam Long vendorId, @RequestPart String product, @RequestPart MultipartFile imageFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Product productObj = objectMapper.readValue(product, Product.class);
        productService.addProduct(vendorId, productObj, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product Added Successfully !");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable long id, @RequestPart String product, @RequestPart MultipartFile imageFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Product productObj = objectMapper.readValue(product, Product.class);
        productService.updateProduct(id, productObj, imageFile);
        return ResponseEntity.ok("Product Updated Successfully !");
    }

    @GetMapping
    public ResponseEntity<ProductSearchResponse> getAllProducts(@RequestParam(required = false) String category,
                                                           @RequestParam(defaultValue = "1", required = false) int pageNumber,
                                                           @RequestParam(defaultValue = "10", required = false) int pageSize) {
        ProductSearchResponse products = productService.getAllProducts(category, pageNumber - 1, pageSize);
        return ResponseEntity.ok(products);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductDTOById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        byte[] imageData = product.getImageData();
        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageData);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<ProductSearchResponse> searchProducts(@PathVariable String keyword,
                                                                @RequestParam(defaultValue = "1", required = false) int pageNumber,
                                                                @RequestParam(defaultValue = "10", required = false) int pageSize) {
        ProductSearchResponse product = productService.searchByKeyword(keyword, pageNumber - 1, pageSize);
        return ResponseEntity.ok(product);

    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<ProductSearchResponse> getProductsByVendor(@PathVariable Long vendorId,
                                                                     @RequestParam(defaultValue = "1", required = false) int pageNumber,
                                                                     @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(productService.getProductsByVendor(vendorId, pageNumber - 1, pageSize));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
