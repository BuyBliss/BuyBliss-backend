package com.commerce.ECommerce.Service;

import com.commerce.ECommerce.Model.Product;
import com.commerce.ECommerce.Repositoy.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class
ProductService {

    @Autowired
    ProductRepo productRepo;

    public void updateProduct(Product product) {
        productRepo.save(product);
    }

    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new RuntimeException("Product not found");
        }
        productRepo.deleteById(id);
    }

    public Optional<Product> getProductById(Long id)
    {
        return productRepo.findById(id);
    }

    public List<Product> getProductsByVendor(Long vendorId) {
        return productRepo.findByVendorId(vendorId);
    }
}
