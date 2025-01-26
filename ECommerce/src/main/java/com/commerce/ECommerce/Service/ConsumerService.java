package com.commerce.ECommerce.Service;

import com.commerce.ECommerce.Model.Entity.Cart;
import com.commerce.ECommerce.Model.Entity.Consumer;
import com.commerce.ECommerce.Model.Entity.Order;
import com.commerce.ECommerce.Model.Response.MyOrdersResponse;
import com.commerce.ECommerce.Model.Response.OrderDTO;
import com.commerce.ECommerce.Model.Response.OrderItemDTO;
import com.commerce.ECommerce.Repositoy.ConsumerRepo;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class ConsumerService {

    @Autowired
    ConsumerRepo consumerRepo;


    public void register(Consumer consumer) {
        consumerRepo.save(consumer);
    }

    public void updateProfile(Consumer consumer) {
        consumerRepo.save(consumer);
    }

    public void deleteConsumer(Long id) {
        consumerRepo.deleteById(id);
    }

    public Consumer getConsumerDetails(Long id) {
        return consumerRepo.getReferenceById(id);
    }

    public Cart getMyCart(Long id) {
        return consumerRepo.getReferenceById(id).getCart();
    }

    public MyOrdersResponse getMyOrders(Long consumerId, int pageNumber, int pageSize, String sortOrder) {
        Pageable p = PageRequest.of(pageNumber, pageSize, sortOrder.equals("ASC") ? Sort.by("billDate").ascending() : Sort.by("billDate").descending());
        Page<Order> orders = consumerRepo.findOrdersByConsumerId(consumerId, p);

        List<OrderDTO> orderDTOList = orders.map(order -> {

            List<OrderItemDTO> orderItemDTOS = order.getOrderItemList().stream()
                    .map(orderItem -> new OrderItemDTO(orderItem.getProduct().getProductName(), orderItem.getProductQuantity())).toList();

            return new OrderDTO(order.getOrderId(), order.getStatus(), order.getPaymentType(), order.getDeliveryAddress(), order.getQuantity(), order.getTotalPrice(),
                    order.getBillDate(), orderItemDTOS);

        }).toList();

        return new MyOrdersResponse(orders.getTotalPages(), orderDTOList);
    }
}
