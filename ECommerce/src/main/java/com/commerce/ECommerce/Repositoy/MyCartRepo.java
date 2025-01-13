package com.commerce.ECommerce.Repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commerce.ECommerce.Model.Cart;

@Repository
public interface MyCartRepo extends JpaRepository<Cart, Long> {
}
