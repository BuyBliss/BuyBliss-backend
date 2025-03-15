package com.commerce.ecommerce.repositoy;

import com.commerce.ecommerce.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    @Modifying          //not used now
    @Query("UPDATE Order o SET o.receipt = :receipt WHERE o.orderId = :id")
    void setReceiptToId(@Param("id") Long id, @Param("receipt") byte[] receipt);
}
