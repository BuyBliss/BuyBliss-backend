package com.commerce.ecommerce.model.request;

import com.commerce.ecommerce.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderUIRequest {
    private Long consumerId;
    private PaymentType paymentType;
    private String deliveryAddress;
}
