package com.commerce.ECommerce.Repositoy;

import com.commerce.ECommerce.Model.MyCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyCartRepo extends JpaRepository<MyCart, Long> {
}
