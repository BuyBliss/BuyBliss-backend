package com.commerce.ECommerce.Service;

import com.commerce.ECommerce.Model.Product;
import com.commerce.ECommerce.Model.Vendor;
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
        if (!optionalProduct.isPresent()) {
            throw new RuntimeException("Product not found");
        }
        productRepo.deleteById(id);
    }

    public Product addProduct(Product product) {
        System.out.println(product.getProductName());
        if (product.getVendor() != null) {
            Long vendorId = product.getVendor().getId();
            Vendor vendor = vendorRepository.findById(vendorId)
                    .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + vendorId));
            product.setVendor(vendor);
        }
        return productRepo.save(product);
    }

    public Optional<Product> getProductById(Long id)
    {
        return productRepo.findById(id);
    }

    public List<Product> getProductsByVendor(Long vendorId) {
        return productRepo.findByVendorId(vendorId);
    }

//    public void addProduct(Product product) {
//        System.out.println(product);
//        productRepo.save(product);
//    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
}
