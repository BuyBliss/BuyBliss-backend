package com.commerce.ECommerce.Repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commerce.ECommerce.Model.Entity.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
	public Vendor findByEmail(String email);
}
