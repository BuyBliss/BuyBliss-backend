package com.commerce.ECommerce.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commerce.ECommerce.Model.Order;
import com.commerce.ECommerce.Model.Product;
import com.commerce.ECommerce.Repositoy.OrderRepo;
import com.commerce.ECommerce.Repositoy.ProductRepo;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepository;
    
    @Autowired
    ProductRepo productRepo;

    public Order createOrder(Long customerId, Long productId, int quantity, String paymentType,String deliveryAddress) {
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setProductId(productId);
        order.setQuatity(quantity);
        order.setPaymentType(paymentType);
        order.setDeliveryAddress(deliveryAddress);
        Optional<Product> product =productRepo.findById(productId);
        order.setTotalPrice(quantity * product.get().getPrice());
        return orderRepository.save(order);
    }
}
