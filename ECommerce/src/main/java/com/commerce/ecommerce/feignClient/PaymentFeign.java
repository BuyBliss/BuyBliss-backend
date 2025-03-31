package com.commerce.ecommerce.feignClient;

import com.commerce.ecommerce.model.enums.PaymentType;
import com.commerce.ecommerce.model.request.PaymentLinkRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "payment-service", url = "http://localhost:8082")
public interface PaymentFeign {

    @PostMapping("/payment/link")
    String getPaymentLink(@RequestBody PaymentLinkRequest paymentLinkRequest);

}
