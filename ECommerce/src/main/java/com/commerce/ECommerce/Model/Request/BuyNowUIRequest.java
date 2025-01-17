package com.commerce.ECommerce.Model.Request;

import com.commerce.ECommerce.Model.Enum.PaymentType;
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
