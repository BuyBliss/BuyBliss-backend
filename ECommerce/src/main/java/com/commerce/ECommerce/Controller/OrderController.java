package com.commerce.ECommerce.Controller;

import com.commerce.ECommerce.Model.Request.PlaceOrderUIRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.commerce.ECommerce.Model.Entity.Order;
import com.commerce.ECommerce.Service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public Order placeOrder(@RequestBody PlaceOrderUIRequest placeOrderUIRequest) {
        return orderService.placeOrder(placeOrderUIRequest);
    }
}