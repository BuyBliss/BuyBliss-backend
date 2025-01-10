package com.commerce.ECommerce.Repositoy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commerce.ECommerce.Model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
  public Cart save(Cart cart);
}
