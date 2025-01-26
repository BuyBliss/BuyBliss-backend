package com.commerce.ECommerce.Controller;


import com.commerce.ECommerce.Model.Entity.Product;
import com.commerce.ECommerce.Model.Response.ProductDTO;
import com.commerce.ECommerce.Model.Response.ProductSearchResponse;
import com.commerce.ECommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestBody Product product) {
        productService.updateProduct(product);
        return ResponseEntity.ok("Product Updated Successfully !");
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(@RequestParam(required = false) String category,
                                                           @RequestParam(defaultValue = "1", required = false) int pageNumber,
                                                           @RequestParam(defaultValue = "10", required = false) int pageSize) {
        try {
            List<ProductDTO> products = productService.getAllProducts(category,pageNumber - 1, pageSize);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Optional<Product> product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> searchProducts(@PathVariable String keyword,
                                            @RequestParam(defaultValue = "1", required = false) int pageNumber,
                                            @RequestParam(defaultValue = "10", required = false) int pageSize) {
        try {
            ProductSearchResponse product = productService.searchByKeyword(keyword, pageNumber - 1, pageSize);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<Product>> getProductsByVendor(@PathVariable Long vendorId) {
        try {
            return ResponseEntity.ok(productService.getProductsByVendor(vendorId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestParam Long vendorId, @RequestBody Product product) {
        productService.addProduct(vendorId, product);
        return ResponseEntity.ok("Product Added Successfully !");
    }
}
