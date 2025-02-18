package com.commerce.ECommerce.Repositoy;

import com.commerce.ECommerce.Model.Entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerRepo extends JpaRepository<Consumer, Long> {
	
	public Consumer findByEmail(String email);
}
