package com.commerce.ECommerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.commerce.ECommerce.Service.VendorService;

@RestController
@RequestMapping("/vendor")
public class VendorController {

	@Autowired
	VendorService vendorService;

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
