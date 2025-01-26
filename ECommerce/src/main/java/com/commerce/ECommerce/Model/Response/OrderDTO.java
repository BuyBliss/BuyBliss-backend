package com.commerce.ECommerce.Model.Response;

import com.commerce.ECommerce.Model.Enum.OrderStatus;
import com.commerce.ECommerce.Model.Enum.PaymentType;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long orderId;
    private OrderStatus status;
    private PaymentType paymentType;
    private String deliveryAddress;
    private int quantity;
    private double totalPrice;
    @Temporal(value = TemporalType.DATE)
    private LocalDate billDate;
    private List<OrderItemDTO> orderItemList;
}
