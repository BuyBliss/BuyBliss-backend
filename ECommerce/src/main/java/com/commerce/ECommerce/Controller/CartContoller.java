package com.commerce.ECommerce.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.ECommerce.Model.Cart;
import com.commerce.ECommerce.Model.Consumer;
import com.commerce.ECommerce.Model.Product;
import com.commerce.ECommerce.Repositoy.ConsumerRepo;
import com.commerce.ECommerce.Repositoy.ProductRepo;
import com.commerce.ECommerce.Service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartContoller {
	
	 @Autowired
	 private ConsumerRepo customerRepository;
	
	 @Autowired
	 ProductRepo productRepository;
	
	 @Autowired
	 CartService  cartService;
	
		@PostMapping("/add")
		public ResponseEntity<String> addToCart(@RequestParam Long customerId, @RequestParam Long productId,
				@RequestParam int quantity) {
			Optional<Consumer> customerOptional = customerRepository.findById(customerId);
			Optional<Product> productOptional = productRepository.findById(productId);
			if (customerOptional.isPresent() && productOptional.isPresent()) {
				cartService.addTocart(customerOptional.get(), productOptional.get(), quantity);
				return ResponseEntity.ok("Product added to cart successfully.");
			} else {
				return ResponseEntity.badRequest().body("Invalid customer ID or product ID.");
			}
		}

	    @DeleteMapping("/emptyCart")
	    public ResponseEntity<String> removeFromCart(@RequestParam Long customerId) {
	    	Optional<Consumer> customerOptional = customerRepository.findById(customerId);
	        if (customerOptional.isPresent()) {
	            Consumer customer = customerOptional.get();
	            Cart cart = customer.getCart();
	            if (cart != null) {
	            	cartService.deleteCart(cart);
	                return ResponseEntity.ok("Product removed from cart successfully.");
	            } else {
	                return ResponseEntity.badRequest().body("Cart is empty.");
	            }
	        } else {
	            return ResponseEntity.badRequest().body("Invalid customer ID.");
	        }
	    }
	}


