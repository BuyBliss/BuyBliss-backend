package com.ecommerce.payment.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.payment.service.RazorpayPaymentService;


@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
@RestController
public class PaymentController {

	@Autowired
	private RazorpayPaymentService razorpayPaymentService;

	@PostMapping("/razorpay/create-order")
	public ResponseEntity<String> createOrder(@RequestBody Map<String, Object> data) {
		try {
			razorpayPaymentService.createOrder(data);
			return ResponseEntity.status(HttpStatus.OK).body("payment initiated");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PostMapping("/payment/capture")
	public ResponseEntity<String> capturePayment(@RequestBody Map<String, Object> data) {
		try {
			razorpayPaymentService.capturePayment(data);
			return ResponseEntity.status(HttpStatus.OK).body("Payment Captured");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
