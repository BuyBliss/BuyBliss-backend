package com.commerce.ECommerce.Repositoy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commerce.ECommerce.Model.Entity.Cart;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
