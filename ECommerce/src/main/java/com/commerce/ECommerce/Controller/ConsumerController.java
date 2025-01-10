package com.commerce.ECommerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.ECommerce.Model.Cart;
import com.commerce.ECommerce.Model.Consumer;
import com.commerce.ECommerce.Service.ConsumerService;


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
    public ResponseEntity<Cart> getMyCart(@PathVariable Long id){
        return ResponseEntity.ok(consumerService.getMyCart(id));
    }

//    @GetMapping("/myOrders/{id}")
//    public ResponseEntity<List<Order>> getMyOrders(@PathVariable Long orderId){
//        return ResponseEntity.ok(consumerService.getMyOrders(orderId));
//    }
}
