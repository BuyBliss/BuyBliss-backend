package com.commerce.ECommerce.Service;

import com.commerce.ECommerce.Model.Entity.Product;
import com.commerce.ECommerce.Model.Entity.Vendor;
import com.commerce.ECommerce.Repositoy.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.commerce.ECommerce.Repositoy.VendorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    VendorRepository vendorRepository;

    public void updateProduct(Product product) {
        productRepo.save(product);
    }

    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        productRepo.deleteById(id);
    }

    public void addProduct(Long vendorId, Product product) {
        System.out.println(product.getProductName());

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + vendorId));
        product.setVendor(vendor);

        productRepo.save(product);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepo.findById(id);
    }

    public List<Product> getProductsByVendor(Long vendorId) {
        return vendorRepository.getReferenceById(vendorId).getProducts();
    }


    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
}
