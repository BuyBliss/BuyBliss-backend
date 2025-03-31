package com.commerce.ecommerce.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderStatus {
    private Long orderId;
    private boolean isPaymentSuccess;
}
