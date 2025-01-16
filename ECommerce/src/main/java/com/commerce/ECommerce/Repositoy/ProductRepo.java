package com.commerce.ECommerce.Repositoy;

import com.commerce.ECommerce.Model.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    //List<Product> findByVendorId(Long vendorId);
}
