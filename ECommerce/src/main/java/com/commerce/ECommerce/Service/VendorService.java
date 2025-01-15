package com.commerce.ECommerce.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commerce.ECommerce.Model.Entity.Vendor;
import com.commerce.ECommerce.Repositoy.VendorRepository;

@Service
public class VendorService {

    @Autowired
    VendorRepository vendorRepository;

    public Vendor createVendor(Vendor vendor) {
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
