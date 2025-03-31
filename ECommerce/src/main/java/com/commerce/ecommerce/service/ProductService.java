package com.commerce.ecommerce.service;

import com.commerce.ecommerce.exception.OutOfStockException;
import com.commerce.ecommerce.model.dto.ProductDTO;
import com.commerce.ecommerce.model.dto.VendorDTO;
import com.commerce.ecommerce.model.entity.OrderItem;
import com.commerce.ecommerce.model.entity.Product;
import com.commerce.ecommerce.model.entity.Vendor;
import com.commerce.ecommerce.model.response.ProductSearchResponse;
import com.commerce.ecommerce.repositoy.ProductRepo;
import com.commerce.ecommerce.repositoy.VendorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    VendorRepository vendorRepository;

    public void updateProduct(long id, Product product, MultipartFile imageFile) throws IOException {
        Vendor vendor = productRepo.getReferenceById(id).getVendor();
        product.setVendor(vendor);
        product.setProductId(id);
        product.setImageData(imageFile.getBytes());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        productRepo.save(product);
    }

    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        productRepo.deleteById(id);
    }

    public void addProduct(Long vendorId, Product product, MultipartFile imageFile) throws IOException {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + vendorId));
        product.setVendor(vendor);
        product.setImageData(imageFile.getBytes());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        productRepo.save(product);
    }

    public Product getProductById(Long id) {
        return productRepo.getReferenceById(id);
    }

    public ProductDTO getProductDTOById(Long id) {
        Product p = productRepo.getReferenceById(id);
        Vendor v = p.getVendor();
        VendorDTO vendorDTO = new VendorDTO(v.getVendorId(), v.getName(), v.getEmail(), v.getContactNo());
        String base64Image = (p.getImageData() != null)
                ? "data:image/png;base64," + Base64.getEncoder().encodeToString(p.getImageData())   // Convert byte[] to Base64
                : null;
        return new ProductDTO(p.getProductId(), p.getProductName(), p.getDescription(), p.getCategory(), p.getSubCategory(), p.getPrice(), p.getStock(), vendorDTO, base64Image);
    }

    public ProductSearchResponse getProductsByVendor(Long vendorId, int pageNumber, int pageSize) {
        Page<Product> products = vendorRepository.getProductsById(vendorId, PageRequest.of(pageNumber, pageSize));

        List<ProductDTO> productDTOS = products.map(p -> {
            Vendor v = p.getVendor();
            VendorDTO vendorDTO = new VendorDTO(v.getVendorId(), v.getName(), v.getEmail(), v.getContactNo());
            String base64Image = (p.getImageData() != null)
                    ? "data:image/png;base64," + Base64.getEncoder().encodeToString(p.getImageData())   // Convert byte[] to Base64
                    : null;
            return new ProductDTO(p.getProductId(), p.getProductName(), p.getDescription(), p.getCategory(), p.getSubCategory(), p.getPrice(), p.getStock(), vendorDTO, base64Image);
        }).toList();

        return new ProductSearchResponse(products.getTotalPages(), productDTOS);
    }


    public ProductSearchResponse getAllProducts(String category, int pageNumber, int pageSize) {
        Page<Product> products;
        if (category != null && !category.isBlank())
            products = productRepo.findByCategory(category, PageRequest.of(pageNumber, pageSize));
        else
            products = productRepo.findAll(PageRequest.of(pageNumber, pageSize));

        List<ProductDTO> productDTOS = products.map(p -> {
            Vendor v = p.getVendor();
            VendorDTO vendorDTO = new VendorDTO(v.getVendorId(), v.getName(), v.getEmail(), v.getContactNo());
            String base64Image = (p.getImageData() != null)
                    ? "data:image/png;base64," + Base64.getEncoder().encodeToString(p.getImageData())   // Convert byte[] to Base64
                    : null;
            return new ProductDTO(p.getProductId(), p.getProductName(), p.getDescription(), p.getCategory(), p.getSubCategory(), p.getPrice(), p.getStock(), vendorDTO, base64Image);
        }).toList();

        return new ProductSearchResponse(products.getTotalPages(), productDTOS);
    }


    public ProductSearchResponse searchByKeyword(String keyword, int pageNumber, int pageSize) {
        Page<Product> products = productRepo.searchProducts(keyword, PageRequest.of(pageNumber, pageSize));

        List<ProductDTO> productDTOS = products.map(p -> {
            Vendor v = p.getVendor();
            VendorDTO vendorDTO = new VendorDTO(v.getVendorId(), v.getName(), v.getEmail(), v.getContactNo());
            String base64Image = (p.getImageData() != null)
                    ? "data:image/png;base64," + Base64.getEncoder().encodeToString(p.getImageData())   // Convert byte[] to Base64
                    : null;
            return new ProductDTO(p.getProductId(), p.getProductName(), p.getDescription(), p.getCategory(), p.getSubCategory(), p.getPrice(), p.getStock(), vendorDTO, base64Image);
        }).toList();

        return new ProductSearchResponse(products.getTotalPages(), productDTOS);
    }

    @Transactional
    public Product holdStock(Long productId, int quantity) {
        Product product = productRepo.findByProductIdWithLock(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            throw new OutOfStockException("Not enough stock available");
        }

        product.setStock(product.getStock() - quantity);
        return productRepo.save(product);
    }

    @Async
    public void releaseInventory(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList) {
            releaseStock(orderItem.getProduct().getProductId(), orderItem.getProductQuantity());
        }
    }

    @Transactional
    private void releaseStock(Long productId, int productQuantity) {
        Product product = productRepo.findByProductIdWithLock(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStock(product.getStock() + productQuantity);
        productRepo.save(product);
    }
}
