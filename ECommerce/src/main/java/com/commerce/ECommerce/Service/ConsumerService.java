package com.commerce.ECommerce.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commerce.ECommerce.Model.Cart;
import com.commerce.ECommerce.Model.Consumer;
import com.commerce.ECommerce.Repositoy.CartRepository;
import com.commerce.ECommerce.Repositoy.ConsumerRepo;
import com.commerce.ECommerce.Repositoy.ProductRepo;

@Service
public class ConsumerService {

    @Autowired
    ConsumerRepo consumerRepo;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepo productRepo;

//    @Autowired
//    MyOrdersRepo myOrdersRepo;

//    @Autowired
//    OrderRepo orderRepo;

    public void register(Consumer consumer) {
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
    	Optional<Consumer> consumer = consumerRepo.findById(id);
         return consumer.get().getCart();
    }
//
//    public List<Order> getMyOrders(Long orderId) {
//        List<Long> orderIdList = myOrdersRepo.findById(orderId).get().getListOfOrdersId();
//        return orderRepo.findAllById(orderIdList);
//    }
}
