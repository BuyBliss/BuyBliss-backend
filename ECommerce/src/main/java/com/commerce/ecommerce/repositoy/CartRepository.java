package com.commerce.ecommerce.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commerce.ecommerce.model.entity.Cart;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
