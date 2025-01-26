package com.commerce.ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/f5StatusCheck")
    public void test()
    {
        System.out.println("Success");
    }
}
