package com.commerce.ecommerce.feignClient;

import com.commerce.ecommerce.model.dto.EmailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "email-service", url = "http://localhost:8081")
public interface EmailFeign {

    @PostMapping("/email/send")
    String sendEmail(@RequestBody EmailDTO emailDTO);

}
