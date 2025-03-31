package com.commerce.ecommerce.model.request;

import com.commerce.ecommerce.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentLinkRequest {
    private Long orderId;
    private PaymentType paymentType;
    private double amount;
}
