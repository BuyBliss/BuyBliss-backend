package com.commerce.ecommerce.model.request;

import com.commerce.ecommerce.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyNowUIRequest {
    private Long productId;
    private Long consumerId;
    private String deliveryAddress;
    private PaymentType paymentType;
}
