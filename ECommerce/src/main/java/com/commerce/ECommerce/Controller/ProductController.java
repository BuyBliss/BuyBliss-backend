package com.commerce.ECommerce.Controller;

import com.commerce.ECommerce.Model.Product;
import com.commerce.ECommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(Product product){
        productService.updateProduct(product);
        return ResponseEntity.ok("Product Updated Successfully !");
    }
}
