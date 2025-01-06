package com.commerce.ECommerce.Service;

import com.commerce.ECommerce.Model.Consumer;
import com.commerce.ECommerce.Model.Order;
import com.commerce.ECommerce.Model.Product;
import com.commerce.ECommerce.Repositoy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerService {

    @Autowired
    ConsumerRepo consumerRepo;

    @Autowired
    MyCartRepo myCartRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    MyOrdersRepo myOrdersRepo;

    @Autowired
    OrderRepo orderRepo;

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

    public List<Product> getMyCart(Long id) {
        List<Long> productIdList = myCartRepo.findById(id).get().getListOfProductId();
        return productRepo.findAllById(productIdList);
    }

    public List<Order> getMyOrders(Long orderId) {
        List<Long> orderIdList = myOrdersRepo.findById(orderId).get().getListOfOrdersId();
        return orderRepo.findAllById(orderIdList);
    }
}
