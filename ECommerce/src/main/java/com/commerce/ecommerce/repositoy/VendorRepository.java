package com.commerce.ecommerce.repositoy;

import com.commerce.ecommerce.model.entity.Product;
import com.commerce.ecommerce.model.entity.Vendor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    @Query("SELECT v.products FROM Vendor v WHERE v.vendorId = :vendorId")
    Page<Product> getProductsById(Long vendorId, Pageable pageable);
}
