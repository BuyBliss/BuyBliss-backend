package com.commerce.ECommerce.Service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.commerce.ECommerce.Dto.LoginDto;
import com.commerce.ECommerce.Model.Entity.Cart;
import com.commerce.ECommerce.Model.Entity.Consumer;
import com.commerce.ECommerce.Model.Entity.Order;
import com.commerce.ECommerce.Repositoy.ConsumerRepo;

@Service
public class ConsumerService {

    @Autowired
    ConsumerRepo consumerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
	public boolean authenticateConsumer(LoginDto dto) {
		Consumer consumer = consumerRepo.findByEmail(dto.getEmail());
		if (Objects.nonNull(consumer)) {
			return passwordEncoder.matches(dto.getPassword(), consumer.getPassword());
		} else
			return false;
	}
    
    public void register(Consumer consumer) {
    	String encodePassword=passwordEncoder.encode(consumer.getPassword());
    	consumer.setPassword(encodePassword);
        consumerRepo.save(consumer);
    }

    public void updateProfile(Consumer consumer) {
        consumerRepo.save(consumer);
    }

    public void deleteConsumer(Long id) {
        consumerRepo.deleteById(id);
    }

    public Consumer getConsumerDetails(Long id) {
        return consumerRepo.getReferenceById(id);
    }

    public Cart getMyCart(Long id) {
        return consumerRepo.getReferenceById(id).getCart();
    }

    public List<Order> getMyOrders(Long consumerId) {
        return consumerRepo.getReferenceById(consumerId).getOrders();
    }
}
