package com.commerce.ecommerce.controller;

import com.commerce.ecommerce.model.request.UpdateCartUIRequest;
import com.commerce.ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@Tag(name = "Shopping Cart", description = "Operations related to shopping cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody UpdateCartUIRequest addRequest) {
        cartService.addToCart(addRequest);
        return ResponseEntity.ok("Product added to cart successfully.");
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeProduct(@RequestBody UpdateCartUIRequest removeRequest) {
        cartService.removeFromCart(removeRequest);
        return ResponseEntity.ok("Removed 1 product successfully.");
    }

    @DeleteMapping("/emptyCart")
    public ResponseEntity<String> emptyCart(@RequestParam Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok("Cart is empty now.");
    }
}


