package com.commerce.ECommerce.Model.Request;

import com.commerce.ECommerce.Model.Enum.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderUIRequest {
    private Long cartId;
    private Long consumerId;
    private double totalPrice;
    private PaymentType paymentType;
    private String deliveryAddress;
}
