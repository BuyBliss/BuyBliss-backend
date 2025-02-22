package com.commerce.ecommerce.controller;

import com.commerce.ecommerce.model.request.BuyNowUIRequest;
import com.commerce.ecommerce.model.request.PlaceOrderUIRequest;
import com.commerce.ecommerce.model.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.commerce.ecommerce.service.OrderService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

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

    @GetMapping("/{orderId}/receipt")
    public ResponseEntity<byte[]> getReceipt(@PathVariable Long orderId) throws ExecutionException, InterruptedException, TimeoutException {
        byte[] receipt = orderService.getReceiptById(orderId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        httpHeaders.setContentDisposition(ContentDisposition.inline().filename("Receipt_" + orderId + ".pdf").build());

        return new ResponseEntity<>(receipt, httpHeaders, HttpStatus.OK);
    }
}