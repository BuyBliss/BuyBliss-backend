package com.commerce.ECommerce.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commerce.ECommerce.Model.Vendor;
import com.commerce.ECommerce.Repositoy.VendorRepository;

@Service
public class VendorService {
	
	@Autowired
	VendorRepository vendorRepository;
	
	 public void deleteVendor(Long id) {
	        Optional<Vendor> optionalVendor  =vendorRepository.findById(id);
	        if (!optionalVendor.isPresent()) {
	            throw new RuntimeException("Vendor not found");
	        }
	        vendorRepository.deleteById(id);
	    }


}
