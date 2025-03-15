package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.dto.OrderDTO;
import com.commerce.ecommerce.model.dto.OrderItemDTO;
import com.commerce.ecommerce.model.entity.*;
import com.commerce.ecommerce.model.enums.OrderStatus;
import com.commerce.ecommerce.model.request.BuyNowUIRequest;
import com.commerce.ecommerce.model.request.PlaceOrderUIRequest;
import com.commerce.ecommerce.repositoy.CartRepository;
import com.commerce.ecommerce.repositoy.OrderItemRepo;
import com.commerce.ecommerce.repositoy.OrderRepo;
import com.commerce.ecommerce.repositoy.ProductRepo;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepository;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    ReceiptService receiptService;

    @Autowired
    OrderItemRepo orderItemRepo;

    @Autowired
    ConsumerService consumerService;

    @Autowired
    CartService cartService;

    @Autowired
    CartRepository cartRepo;

    @Autowired
    KafkaService kafkaService;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public OrderDTO placeOrder(PlaceOrderUIRequest placeOrderUIRequest) {
        Order order = new Order();
        order.setConsumer(consumerService.getConsumerDetails(placeOrderUIRequest.getConsumerId()));
        order.setDeliveryAddress(placeOrderUIRequest.getDeliveryAddress());

        Cart cart = cartRepo.getReferenceById(placeOrderUIRequest.getCartId());

        order.setQuantity(cart.getSize());
        order.setPaymentType(placeOrderUIRequest.getPaymentType());
        order.setStatus(OrderStatus.CONFIRMED);
        order.setBillDate(LocalDate.now());

        Order savedOrder = orderRepository.save(order);

        double totalPrice = 0;

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItemList()) {
            Product product = cartItem.getProduct();
            int quantity = cartItem.getProductQuantity();
            product.setStock(product.getStock() - quantity);
            productRepo.save(product);

            totalPrice += product.getPrice() * quantity;

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setProductQuantity(quantity);
            orderItem.setOrder(savedOrder);
            orderItems.add(orderItem);
            orderItemRepo.save(orderItem);
        }

        cartService.emptyCart(cart);

        savedOrder.setTotalPrice(totalPrice);

        savedOrder.setOrderItemList(orderItems);
        orderRepository.save(savedOrder);

        receiptService.generateReceipt(savedOrder);
        //send receipt over mail

        return mapOrderToOrderDTO(savedOrder);
    }

    @Transactional
    public OrderDTO buyNow(BuyNowUIRequest buyNowUIRequest) {
        Order order = new Order();
        order.setConsumer(consumerService.getConsumerDetails(buyNowUIRequest.getConsumerId()));

        Product product = productRepo.getReferenceById(buyNowUIRequest.getProductId());
        product.setStock(product.getStock() - 1);
        productRepo.save(product);

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setProductQuantity(1);

        // 🔥 Ensure OrderItem is linked to Order BEFORE saving
        orderItem.setOrder(order);
        order.getOrderItemList().add(orderItem);

        order.setTotalPrice(product.getPrice());
        order.setQuantity(1);
        order.setDeliveryAddress(buyNowUIRequest.getDeliveryAddress());
        order.setPaymentType(buyNowUIRequest.getPaymentType());
        order.setStatus(OrderStatus.CONFIRMED);
        order.setBillDate(LocalDate.now());

        Order savedOrder = orderRepository.save(order);

        postOrder(savedOrder.getOrderId());

        return mapOrderToOrderDTO(savedOrder);
    }

    @Async                                  //working synchronously
    private void postOrder(Long orderId) {
        Order order = orderRepository.getReferenceById(orderId);
        kafkaService.sendEmail(order);
        receiptService.generateReceipt(order);
    }

    private OrderDTO mapOrderToOrderDTO(Order savedOrder) {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setOrderId(savedOrder.getOrderId());
        orderDTO.setTotalPrice(savedOrder.getTotalPrice());
        orderDTO.setQuantity(savedOrder.getQuantity());
        orderDTO.setDeliveryAddress(savedOrder.getDeliveryAddress());
        orderDTO.setPaymentType(savedOrder.getPaymentType());
        orderDTO.setStatus(savedOrder.getStatus());
        orderDTO.setBillDate(savedOrder.getBillDate());

        List<OrderItemDTO> orderItemDTOs = savedOrder.getOrderItemList().stream()
                .map(orderItem -> {
                    OrderItemDTO orderItemDTO = new OrderItemDTO();
                    orderItemDTO.setProductName(orderItem.getProduct().getProductName());
                    orderItemDTO.setProductQuantity(orderItem.getProductQuantity());
                    return orderItemDTO;
                })
                .collect(Collectors.toList());

        orderDTO.setOrderItemList(orderItemDTOs);

        return orderDTO;
    }

    public byte[] getReceiptById(Long orderId) throws ExecutionException, InterruptedException, TimeoutException {
        Order order = orderRepository.getReferenceById(orderId);
        byte[] receipt = order.getReceipt();
        if (receipt != null) {
            return receipt;  // Return immediately if found
        }
        CompletableFuture<byte[]> receiptFuture = receiptService.generateReceipt(order);
        return receiptFuture.get(10, TimeUnit.SECONDS); // Wait for max 10s
    }
}
