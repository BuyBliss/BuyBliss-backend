package com.commerce.ECommerce.Repositoy;

import com.commerce.ECommerce.Model.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

}
