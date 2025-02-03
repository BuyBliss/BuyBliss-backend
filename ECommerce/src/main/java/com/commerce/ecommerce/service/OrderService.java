package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.entity.Cart;
import com.commerce.ecommerce.model.entity.Order;
import com.commerce.ecommerce.model.entity.OrderItem;
import com.commerce.ecommerce.model.entity.Product;
import com.commerce.ecommerce.model.enums.OrderStatus;
import com.commerce.ecommerce.model.request.BuyNowUIRequest;
import com.commerce.ecommerce.model.request.PlaceOrderUIRequest;
import com.commerce.ecommerce.model.dto.OrderDTO;
import com.commerce.ecommerce.model.dto.OrderItemDTO;
import com.commerce.ecommerce.repositoy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    CartService cartService;

    @Autowired
    CartRepository cartRepo;

    public OrderDTO placeOrder(PlaceOrderUIRequest placeOrderUIRequest) {
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
        orderRepository.save(savedOrder);
        return mapOrderToOrderDTO(savedOrder);
    }

    public OrderDTO buyNow(BuyNowUIRequest buyNowUIRequest) {
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

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        savedOrder.setOrderItemList(orderItems);
        orderRepository.save(savedOrder);
        return mapOrderToOrderDTO(savedOrder);
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

}
