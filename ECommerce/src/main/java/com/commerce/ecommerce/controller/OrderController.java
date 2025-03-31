package com.commerce.ecommerce.controller;

import com.commerce.ecommerce.model.enums.OrderStatus;
import com.commerce.ecommerce.model.request.CreateOrderUIRequest;
import com.commerce.ecommerce.model.request.PlaceOrderUIRequest;
import com.commerce.ecommerce.model.dto.OrderDTO;
import com.commerce.ecommerce.model.request.UpdateOrderStatus;
import com.commerce.ecommerce.model.response.CreatedOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.commerce.ecommerce.service.OrderService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/order")
@Tag(name = "Order Management", description = "Operations related to orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public OrderDTO placeOrder(@RequestBody PlaceOrderUIRequest placeOrderUIRequest) {
        return orderService.placeOrder(placeOrderUIRequest);
    }

    @PostMapping("/buyNow")
    public OrderDTO buyNow(@RequestBody CreateOrderUIRequest createOrderUIRequest) {
        return orderService.buyNow(createOrderUIRequest);
    }

    @GetMapping("/{orderId}/receipt")
    public ResponseEntity<byte[]> getReceipt(@PathVariable Long orderId) throws ExecutionException, InterruptedException, TimeoutException {
        byte[] receipt = orderService.getReceiptById(orderId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        httpHeaders.setContentDisposition(ContentDisposition.inline().filename("Receipt_" + orderId + ".pdf").build());

        return new ResponseEntity<>(receipt, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/create")
    public CreatedOrder createOrder(CreateOrderUIRequest createOrderUIRequest) {
        return orderService.initOrder(createOrderUIRequest);
    }

    @PutMapping("/update")
    public void updateOrderStatus(@RequestBody UpdateOrderStatus updateOrderStatus) {
        orderService.finalizeOrder(updateOrderStatus);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<OrderStatus> getOrderStatus(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getStatusForOrderId(id));
    }
}