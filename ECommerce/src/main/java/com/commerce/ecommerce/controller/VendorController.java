package com.commerce.ecommerce.controller;

import com.commerce.ecommerce.model.dto.VendorDTO;
import com.commerce.ecommerce.model.entity.Vendor;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.commerce.ecommerce.service.VendorService;

@RestController
@RequestMapping("/vendor")
@Tag(name = "Vendor Management", description = "Operations related to vendors")
public class VendorController {

    @Autowired
    VendorService vendorService;

    @PostMapping("/signup")
    public ResponseEntity<Vendor> createVendor(@Valid @RequestBody Vendor vendor) {
        Vendor createdVendor = vendorService.createVendor(vendor);
        return new ResponseEntity<>(createdVendor, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateVendor(@PathVariable Long id, @Valid @RequestBody VendorDTO updateVendor) {
        vendorService.updateVendor(id, updateVendor);
        return ResponseEntity.ok("Vendor details updated successfully!");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.ok("Vendor deleted ");
    }
}
