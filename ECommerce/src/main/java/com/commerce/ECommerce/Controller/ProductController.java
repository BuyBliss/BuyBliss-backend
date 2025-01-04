package com.commerce.ECommerce.Controller;

import com.commerce.ECommerce.Model.Product;
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
    public ResponseEntity<String> updateProduct(Product product) {
        productService.updateProduct(product);
        return ResponseEntity.ok("Product Updated Successfully !");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Optional<Product> product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

        @GetMapping("/vendor/{vendorId}")
        public ResponseEntity<List<Product>> getProductsByVendor(@PathVariable Long vendorId) {
            try {
                List<Product> products = productService.getProductsByVendor(vendorId);
                return ResponseEntity.ok(products);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }


    }
}
