package com.commerce.ECommerce.Service;

import com.commerce.ECommerce.Model.Entity.Product;
import com.commerce.ECommerce.Model.Entity.Vendor;
import com.commerce.ECommerce.Model.Response.ProductDTO;
import com.commerce.ECommerce.Model.Response.ProductSearchResponse;
import com.commerce.ECommerce.Model.Response.VendorDTO;
import com.commerce.ECommerce.Repositoy.ProductRepo;
import com.commerce.ECommerce.Repositoy.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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


    public List<ProductDTO> getAllProducts(String category, int pageNumber, int pageSize) {
        Page<Product> products;
        if(category != null && !category.isBlank())
            products = productRepo.findByCategory(category, PageRequest.of(pageNumber, pageSize));
        else
            products = productRepo.findAll(PageRequest.of(pageNumber, pageSize));

        return products.map(p -> {
            Vendor v = p.getVendor();
            VendorDTO vendorDTO = new VendorDTO(v.getVendorId(), v.getName(), v.getEmail(), v.getContactNo());
            return new ProductDTO(p.getProductId(), p.getProductName(), p.getDescription(), p.getCategory(), p.getSubCategory(), p.getPrice(), p.getStock(), vendorDTO);
        }).toList();

    }


    public ProductSearchResponse searchByKeyword(String keyword, int pageNumber, int pageSize) {
        Page<Product> products = productRepo.searchProducts(keyword, PageRequest.of(pageNumber, pageSize));
        System.out.println(products);
        List<ProductDTO> productDTOS = products.map(p -> {
            Vendor v = p.getVendor();
            VendorDTO vendorDTO = new VendorDTO(v.getVendorId(), v.getName(), v.getEmail(), v.getContactNo());
            return new ProductDTO(p.getProductId(), p.getProductName(), p.getDescription(), p.getCategory(), p.getSubCategory(), p.getPrice(), p.getStock(), vendorDTO);
        }).toList();
        System.out.println(productDTOS);
        return new ProductSearchResponse(products.getTotalPages(), productDTOS);
    }
}
