package com.commerce.ecommerce.controller;

import com.commerce.ecommerce.model.entity.Cart;
import com.commerce.ecommerce.model.entity.Consumer;
import com.commerce.ecommerce.model.response.MyOrdersResponse;
import com.commerce.ecommerce.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    ConsumerService consumerService;

    @PostMapping("/signup")
    public ResponseEntity<Consumer> register(@RequestBody Consumer consumer) {
        Consumer createdConsumer = consumerService.register(consumer);
        return new ResponseEntity<>(createdConsumer, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Consumer> updateProfile(@RequestBody Consumer consumer) {
        Consumer consumer1 = consumerService.updateProfile(consumer);
        return new ResponseEntity<>(consumer1, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteProfile(@PathVariable Long id){
        consumerService.deleteConsumer(id);
        return ResponseEntity.ok("Your account has been deleted successfully!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consumer> getConsumer(@PathVariable Long id){
        return ResponseEntity.ok(consumerService.getConsumerDetails(id));
    }

    @GetMapping("/{id}/myCart")
    public ResponseEntity<Cart> getMyCart(@PathVariable Long id){
        return ResponseEntity.ok(consumerService.getMyCart(id));
    }

    @GetMapping("/{consumerId}/myOrders")
    public ResponseEntity<MyOrdersResponse> getMyOrders(@PathVariable Long consumerId,
                                                        @RequestParam(defaultValue = "1", required = false) int pageNumber,
                                                        @RequestParam(defaultValue = "5", required = false) int pageSize,
                                                        @RequestParam(defaultValue = "DESC", required = false) String sortOrder){
        return ResponseEntity.ok(consumerService.getMyOrders(consumerId, pageNumber - 1, pageSize, sortOrder));
    }
}
