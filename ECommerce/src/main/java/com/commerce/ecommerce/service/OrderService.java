package com.commerce.ecommerce.service;

import com.commerce.ecommerce.feignClient.PaymentFeign;
import com.commerce.ecommerce.model.dto.OrderDTO;
import com.commerce.ecommerce.model.dto.OrderItemDTO;
import com.commerce.ecommerce.model.entity.*;
import com.commerce.ecommerce.model.enums.OrderStatus;
import com.commerce.ecommerce.model.request.CreateOrderUIRequest;
import com.commerce.ecommerce.model.request.PaymentLinkRequest;
import com.commerce.ecommerce.model.request.PlaceOrderUIRequest;
import com.commerce.ecommerce.model.request.UpdateOrderStatus;
import com.commerce.ecommerce.model.response.CreatedOrder;
import com.commerce.ecommerce.repositoy.CartRepository;
import com.commerce.ecommerce.repositoy.OrderRepo;
import com.commerce.ecommerce.repositoy.ProductRepo;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepository;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    ProductService productService;

    @Autowired
    ReceiptService receiptService;

    @Autowired
    ConsumerService consumerService;

    @Autowired
    CartService cartService;

    @Autowired
    CartRepository cartRepo;

    @Autowired
    KafkaService kafkaService;

    @Autowired
    PaymentFeign paymentFeign;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public OrderDTO placeOrder(PlaceOrderUIRequest placeOrderUIRequest) {
        Order order = new Order();
        order.setConsumer(consumerService.getConsumerDetails(placeOrderUIRequest.getConsumerId()));
        order.setDeliveryAddress(placeOrderUIRequest.getDeliveryAddress());

        Cart cart = order.getConsumer().getCart();

        order.setQuantity(cart.getSize());
        order.setPaymentType(placeOrderUIRequest.getPaymentType());
        order.setStatus(OrderStatus.CONFIRMED);
        order.setBillDate(LocalDate.now());

        double totalPrice = 0;

        for (CartItem cartItem : cart.getCartItemList()) {
            Product product = cartItem.getProduct();
            int quantity = cartItem.getProductQuantity();
            product.setStock(product.getStock() - quantity);
            productRepo.save(product);

            totalPrice += product.getPrice() * quantity;

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setProductQuantity(quantity);
            // Ensure OrderItem is linked to Order BEFORE saving
            orderItem.setOrder(order);
            order.getOrderItemList().add(orderItem);
        }

        cartService.emptyCart(cart);

        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        postOrder(savedOrder.getOrderId());

        return mapOrderToOrderDTO(savedOrder);
    }

    @Transactional
    public OrderDTO buyNow(CreateOrderUIRequest createOrderUIRequest) {
        Order order = new Order();
        order.setConsumer(consumerService.getConsumerDetails(createOrderUIRequest.getConsumerId()));

        Product product = productRepo.getReferenceById(createOrderUIRequest.getProductId());
        product.setStock(product.getStock() - 1);
        productRepo.save(product);

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setProductQuantity(1);

        // Ensure OrderItem is linked to Order BEFORE saving
        orderItem.setOrder(order);
        order.getOrderItemList().add(orderItem);

        order.setTotalPrice(product.getPrice());
        order.setQuantity(1);
        order.setDeliveryAddress(createOrderUIRequest.getDeliveryAddress());
        order.setPaymentType(createOrderUIRequest.getPaymentType());
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

    /// currently covered buy now scenario only
    @Transactional
    public CreatedOrder initOrder(CreateOrderUIRequest createOrderUIRequest) {
        CreatedOrder response = new CreatedOrder();
        //check stock & hold inventory, return orderItemList
        Product product = productService.holdStock(createOrderUIRequest.getProductId(), 1);


        //save order with pending status
        Order order = new Order();
        order.setConsumer(consumerService.getConsumerDetails(createOrderUIRequest.getConsumerId()));
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setProductQuantity(1);

        // Ensure OrderItem is linked to Order BEFORE saving
        orderItem.setOrder(order);
        order.getOrderItemList().add(orderItem);

        order.setTotalPrice(product.getPrice());
        order.setQuantity(1);
        order.setDeliveryAddress(createOrderUIRequest.getDeliveryAddress());
        order.setPaymentType(createOrderUIRequest.getPaymentType());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderCreated(LocalDateTime.now());
        order.setCartOrder(false);

        Order savedOrder = orderRepository.save(order);

        //get payment link
        String url = paymentFeign.getPaymentLink(new PaymentLinkRequest(savedOrder.getOrderId(), savedOrder.getPaymentType(), savedOrder.getTotalPrice()));

        response.setOrderId(savedOrder.getOrderId());
        response.setPaymentLink(url);
        return response;
    }

    /// if payment success
    //update order status to CONFIRMED     -> if fails -> refund mail
    //if cart order ->  clear cart
    //push notification to UI
    //postOrderCall

    /// if payment fails
    //mark CANCELLED
    //release inventory
    /// common for buy now and cart order
    @Transactional
    public void finalizeOrder(UpdateOrderStatus updateOrderStatus) {
        Order order = orderRepository.getReferenceById(updateOrderStatus.getOrderId());
        if(updateOrderStatus.isPaymentSuccess()) {
            order.setStatus(OrderStatus.CONFIRMED);
            if(order.isCartOrder()){
                cartService.emptyCart(order.getConsumer().getCart());
            }
            //push notification to UI (optional now)        ==      UI needs to do polling to check order status updates from database
            order.setBillDate(LocalDate.now());
            postOrder(order.getOrderId());
        } else {
            order.setStatus(OrderStatus.CANCELLED);
            productService.releaseInventory(order.getOrderItemList());
        }

        orderRepository.save(order);
    }

    public OrderStatus getStatusForOrderId(Long id) {
        return orderRepository.getReferenceById(id).getStatus();
    }
}
