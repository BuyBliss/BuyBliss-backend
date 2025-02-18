package com.commerce.ECommerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.ECommerce.Dto.LoginDto;
import com.commerce.ECommerce.Model.Entity.Cart;
import com.commerce.ECommerce.Model.Entity.Consumer;
import com.commerce.ECommerce.Model.Entity.Order;
import com.commerce.ECommerce.Service.ConsumerService;


@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    ConsumerService consumerService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
		try {
			if (consumerService.authenticateConsumer(loginDto))
				return new ResponseEntity<>("Userlogin Sucesfull", HttpStatus.OK);
			else
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Inavalid UserName or Password");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
		}
	}
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

    @GetMapping("/myOrders/{id}")
    public ResponseEntity<List<Order>> getMyOrders(@PathVariable Long consumerId){
        return ResponseEntity.ok(consumerService.getMyOrders(consumerId));
    }
}
