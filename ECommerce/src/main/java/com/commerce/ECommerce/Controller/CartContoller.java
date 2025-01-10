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
import com.commerce.ECommerce.Model.MyCart;
import com.commerce.ECommerce.Model.Product;
import com.commerce.ECommerce.Repositoy.CartRepository;
import com.commerce.ECommerce.Repositoy.ConsumerRepo;
import com.commerce.ECommerce.Repositoy.ProductRepo;

@RestController
@RequestMapping("/api/cart")
public class CartContoller {
	
	 @Autowired
	 private ConsumerRepo customerRepository;
	
	 @Autowired
	 ProductRepo productRepository;
	
	 @Autowired
	 CartRepository cartRepository;
	
	   @PostMapping("/add")	
	    public ResponseEntity<String> addToCart(@RequestParam Long customerId, @RequestParam Long productId, @RequestParam int quantity) {
	        Optional<Consumer> customerOptional = customerRepository.findById(customerId);
	        Optional<Product> productOptional = productRepository.findById(productId);

	        if (customerOptional.isPresent() && productOptional.isPresent()) {
	            Consumer customer = customerOptional.get();
	            Product product = productOptional.get();

	            Cart cart = customer.getCart();
	            if (cart == null) {
	                cart = new Cart();
	                customer.setCart(cart);
	            }

	            MyCart cartItem = new MyCart();
	            cartItem.setProduct(product);
	            cartItem.setQuantity(quantity);

	            cart.getItems().add(cartItem);
	            cartRepository.save(cart);

	            return ResponseEntity.ok("Product added to cart successfully.");
	        } else {
	            return ResponseEntity.badRequest().body("Invalid customer ID or product ID.");
	        }
	    }

	    @DeleteMapping("/remove")
	    public ResponseEntity<String> removeFromCart(@RequestParam Long customerId, @RequestParam Long productId) {
	        Optional<Consumer> customerOptional = customerRepository.findById(customerId);

	        if (customerOptional.isPresent()) {
	            Consumer customer = customerOptional.get();
	            Cart cart = customer.getCart();

	            if (cart != null) {
	                cart.getItems().removeIf(item -> item.getProduct().getProductId().equals(productId));
	                cartRepository.save(cart);
	                return ResponseEntity.ok("Product removed from cart successfully.");
	            } else {
	                return ResponseEntity.badRequest().body("Cart is empty.");
	            }
	        } else {
	            return ResponseEntity.badRequest().body("Invalid customer ID.");
	        }
	    }
	}


