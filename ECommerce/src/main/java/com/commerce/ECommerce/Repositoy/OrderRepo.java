package com.commerce.ECommerce.Repositoy;

import com.commerce.ECommerce.Model.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
}
