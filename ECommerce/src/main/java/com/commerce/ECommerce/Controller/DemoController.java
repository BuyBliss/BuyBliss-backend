package com.commerce.ECommerce.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/f5StatusCheck")
    public String test()
    {
        return "Success";
    }
}
