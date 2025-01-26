package com.commerce.ecommerce.controller;

import com.commerce.ecommerce.model.request.BuyNowUIRequest;
import com.commerce.ecommerce.model.request.PlaceOrderUIRequest;
import com.commerce.ecommerce.model.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.commerce.ecommerce.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public OrderDTO placeOrder(@RequestBody PlaceOrderUIRequest placeOrderUIRequest) {
        return orderService.placeOrder(placeOrderUIRequest);
    }

    @PostMapping("/buyNow")
    public OrderDTO buyNow(@RequestBody BuyNowUIRequest buyNowUIRequest) {
        return orderService.buyNow(buyNowUIRequest);
    }
}