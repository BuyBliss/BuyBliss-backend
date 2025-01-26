package com.commerce.ECommerce.Controller;

import com.commerce.ECommerce.Model.Entity.Cart;
import com.commerce.ECommerce.Model.Entity.Consumer;
import com.commerce.ECommerce.Model.Response.MyOrdersResponse;
import com.commerce.ECommerce.Service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    ConsumerService consumerService;

    @PostMapping("/add")
    public ResponseEntity<String> register(@RequestBody Consumer consumer) {
        consumerService.register(consumer);
        return ResponseEntity.ok("Welcome " + consumer.getName() + ", You have registered Successfully !");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProfile(@RequestBody Consumer consumer) {
        consumerService.updateProfile(consumer);
        return ResponseEntity.ok("Profile Updated !");
    }

    @DeleteMapping("delete/{id}")
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

    @GetMapping("/{consumerId}/myOrders")
    public ResponseEntity<MyOrdersResponse> getMyOrders(@PathVariable Long consumerId,
                                                        @RequestParam(defaultValue = "1", required = false) int pageNumber,
                                                        @RequestParam(defaultValue = "5", required = false) int pageSize,
                                                        @RequestParam(defaultValue = "DESC", required = false) String sortOrder){
        return ResponseEntity.ok(consumerService.getMyOrders(consumerId, pageNumber - 1, pageSize, sortOrder));
    }
}
