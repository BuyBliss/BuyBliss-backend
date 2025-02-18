package com.commerce.ECommerce.Service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.commerce.ECommerce.Dto.LoginDto;
import com.commerce.ECommerce.Model.Entity.Vendor;
import com.commerce.ECommerce.Repositoy.VendorRepository;

@Service
public class VendorService {

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
	public boolean authenticateVendor(LoginDto loginDto) {
		Vendor vendor = vendorRepository.findByEmail(loginDto.getEmail());
		if (Objects.nonNull(vendor)) {
			return passwordEncoder.matches(loginDto.getPassword(), vendor.getPassword());
		} else
			return false;
	}
    
    public Vendor createVendor(Vendor vendor) {
    	
    	String encodePassword=passwordEncoder.encode(vendor.getPassword());
    	vendor.setPassword(encodePassword);
        return vendorRepository.save(vendor);
    }

    public Vendor updateVendor(Long id, Vendor updatedVendor) {
        Optional<Vendor> existingVendorOptional = vendorRepository.findById(id);

        if (existingVendorOptional.isPresent()) {
            Vendor existingVendor = existingVendorOptional.get();
            existingVendor.setName(updatedVendor.getName());
            existingVendor.setEmail(updatedVendor.getEmail());
            existingVendor.setContactNo(updatedVendor.getContactNo());
            return vendorRepository.save(existingVendor);
        } else {
            throw new RuntimeException("Vendor with ID " + id + " not found");
        }
    }

    public void deleteVendor(Long id) {
        Optional<Vendor> optionalVendor = vendorRepository.findById(id);
        if (optionalVendor.isEmpty()) {
            throw new RuntimeException("Vendor not found");
        }
        vendorRepository.deleteById(id);
    }

	


}
