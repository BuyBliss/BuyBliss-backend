package com.commerce.ECommerce.Service;

import com.commerce.ECommerce.Model.Product;
import com.commerce.ECommerce.Repositoy.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    public void updateProduct(Product product) {
        productRepo.save(product);
    }
}
