package com.ecommerce.payment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.payment.model.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction,Long> {
    public List<Transaction>  findByOrderId(int orderId);
}
