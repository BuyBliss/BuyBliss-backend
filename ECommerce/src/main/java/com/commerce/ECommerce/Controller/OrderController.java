package com.commerce.ECommerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.ECommerce.Model.Order;
import com.commerce.ECommerce.Model.PaymentType;
import com.commerce.ECommerce.Service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public Order createOrder(
            @RequestParam Long customerId,
            @RequestParam Long productId,
            @RequestParam int quantity,
            @RequestParam String paymentType,
            @RequestParam String deliveryAddress) {
        return orderService.createOrder(customerId, productId, quantity, paymentType,deliveryAddress);
    }
}