package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.dto.VendorDTO;
import com.commerce.ecommerce.model.entity.Vendor;
import com.commerce.ecommerce.repositoy.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendorService {

    @Autowired
    VendorRepository vendorRepository;

    public Vendor createVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public void updateVendor(Long id, VendorDTO vendor) {
        Optional<Vendor> existingVendorOptional = vendorRepository.findById(id);

        if (existingVendorOptional.isPresent()) {
            Vendor existingVendor = existingVendorOptional.get();
            existingVendor.setName(vendor.getName());
            existingVendor.setEmail(vendor.getEmail());
            existingVendor.setContactNo(vendor.getContactNo());
            vendorRepository.save(existingVendor);
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
