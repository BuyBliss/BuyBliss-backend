package com.commerce.ECommerce.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.commerce.ECommerce.Model.Entity.*;
import com.commerce.ECommerce.Model.Enum.OrderStatus;
import com.commerce.ECommerce.Model.Request.BuyNowUIRequest;
import com.commerce.ECommerce.Model.Request.PlaceOrderUIRequest;
import com.commerce.ECommerce.Repositoy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepository;
    
    @Autowired
    ProductRepo productRepo;

    @Autowired
    CartItemRepo cartItemRepo;

    @Autowired
    OrderItemRepo orderItemRepo;

    @Autowired
    ConsumerService consumerService;

    @Autowired
    CartService  cartService;

    @Autowired
    CartRepository cartRepo;

    public Order placeOrder(PlaceOrderUIRequest placeOrderUIRequest) {
        Order order = new Order();
        order.setConsumer(consumerService.getConsumerDetails(placeOrderUIRequest.getConsumerId()));
        order.setTotalPrice(placeOrderUIRequest.getTotalPrice());
        order.setDeliveryAddress(placeOrderUIRequest.getDeliveryAddress());

        Cart cart = cartRepo.getReferenceById(placeOrderUIRequest.getCartId());

        order.setQuantity(cart.getSize());
        order.setPaymentType(placeOrderUIRequest.getPaymentType());
        order.setStatus(OrderStatus.CONFIRMED);
        order.setBillDate(LocalDate.now());

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = cart.getCartItemList().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            int quantity = cartItem.getProductQuantity();
            product.setStock(product.getStock() - quantity);
            productRepo.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setProductQuantity(quantity);
            orderItem.setOrder(savedOrder);
            return orderItemRepo.save(orderItem);
        }).collect(Collectors.toList());

        cartService.emptyCart(cart);

        savedOrder.setOrderItemList(orderItems);
        return orderRepository.save(savedOrder);
    }

    public Order buyNow(BuyNowUIRequest buyNowUIRequest) {
        Order order = new Order();
        order.setConsumer(consumerService.getConsumerDetails(buyNowUIRequest.getConsumerId()));

        Product product = productRepo.getReferenceById(buyNowUIRequest.getProductId());
        product.setStock(product.getStock() - 1);
        productRepo.save(product);

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setProductQuantity(1);


        order.setTotalPrice(product.getPrice());
        order.setQuantity(1);
        order.setDeliveryAddress(buyNowUIRequest.getDeliveryAddress());
        order.setPaymentType(buyNowUIRequest.getPaymentType());
        order.setStatus(OrderStatus.CONFIRMED);
        order.setBillDate(LocalDate.now());

        Order savedOrder = orderRepository.save(order);
        orderItem.setOrder(savedOrder);
        orderItemRepo.save(orderItem);

        return order;
    }
}
