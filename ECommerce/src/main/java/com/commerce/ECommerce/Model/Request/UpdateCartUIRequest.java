package com.commerce.ECommerce.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartUIRequest {
    long cartId;
    long productId;

    public long getCartId() {
        return cartId;
    }

    public long getProductId() {
        return productId;
    }
}
