package com.commerce.ECommerce.Controller;

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

import com.commerce.ECommerce.Model.Entity.Vendor;
import com.commerce.ECommerce.Service.VendorService;

@RestController
@RequestMapping("/vendor")
public class VendorController {

	@Autowired
	VendorService vendorService;
	
	 @PostMapping("/add")
	    public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor) {
	        Vendor createdVendor = vendorService.createVendor(vendor);
	        return new ResponseEntity<>(createdVendor, HttpStatus.CREATED);
	    }

	    @PutMapping("/update/{id}")
	    public ResponseEntity<Vendor> updateVendor(
	            @PathVariable Long id, 
	            @RequestBody Vendor updatedVendor) {
	        Vendor vendor = vendorService.updateVendor(id, updatedVendor);
	        return new ResponseEntity<>(vendor, HttpStatus.OK);
	    }

	@DeleteMapping("deleteVendor/{id}")
	public ResponseEntity<?> deleteVendor(@PathVariable Long id) {
		try {
			vendorService.deleteVendor(id);
			return ResponseEntity.ok("Vendor deleted ");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}
