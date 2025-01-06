package com.commerce.ECommerce.Controller;

import com.commerce.ECommerce.Model.Consumer;
import com.commerce.ECommerce.Model.Order;
import com.commerce.ECommerce.Model.Product;
import com.commerce.ECommerce.Service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    ConsumerService consumerService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody Consumer consumer) {
        consumerService.register(consumer);
        return ResponseEntity.ok("Welcome " + consumer.getName() + ", You have registered Successfully !");
    }

    @PutMapping
    public ResponseEntity<String> updateProfile(@RequestBody Consumer consumer) {
        consumerService.updateProfile(consumer);
        return ResponseEntity.ok("Profile Updated !");
    }

    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable Long id){
        consumerService.deleteConsumer(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consumer> getConsumer(@PathVariable Long id){
        return ResponseEntity.ok(consumerService.getConsumerDetails(id));
    }

    @GetMapping("/myCart/{id}")
    public ResponseEntity<List<Product>> getMyCart(@PathVariable Long cartId){
        return ResponseEntity.ok(consumerService.getMyCart(cartId));
    }

    @GetMapping("/myOrders/{id}")
    public ResponseEntity<List<Order>> getMyOrders(@PathVariable Long orderId){
        return ResponseEntity.ok(consumerService.getMyOrders(orderId));
    }
}
