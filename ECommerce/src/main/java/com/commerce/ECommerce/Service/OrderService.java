package com.commerce.ECommerce.Service;

import java.time.LocalDate;
import java.util.Optional;

import com.commerce.ECommerce.Model.Entity.Cart;
import com.commerce.ECommerce.Model.Enum.OrderStatus;
import com.commerce.ECommerce.Model.Enum.PaymentType;
import com.commerce.ECommerce.Model.Request.PlaceOrderUIRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commerce.ECommerce.Model.Entity.Order;
import com.commerce.ECommerce.Model.Entity.Product;
import com.commerce.ECommerce.Repositoy.OrderRepo;
import com.commerce.ECommerce.Repositoy.ProductRepo;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepository;
    
    @Autowired
    ProductRepo productRepo;

    @Autowired
    ConsumerService consumerService;

    public Order placeOrder(PlaceOrderUIRequest placeOrderUIRequest) {
        Order order = new Order();
        order.setConsumer(consumerService.getConsumerDetails(placeOrderUIRequest.getConsumerId()));
        order.setTotalPrice(placeOrderUIRequest.getTotalPrice());
        order.setDeliveryAddress(placeOrderUIRequest.getDeliveryAddress());
        Cart cart = consumerService.getMyCart(placeOrderUIRequest.getCartId());
        order.setQuantity(cart.getSize());
        order.setCartItemList(cart.getCartItemList());
        order.setPaymentType(placeOrderUIRequest.getPaymentType());
        order.setStatus(OrderStatus.CONFIRMED);
        order.setBillDate(LocalDate.now());
        return orderRepository.save(order);
    }
}
