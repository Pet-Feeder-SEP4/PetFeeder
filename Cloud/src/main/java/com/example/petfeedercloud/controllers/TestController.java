package com.example.petfeedercloud.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    //http://localhost:8080/swagger-ui/index.html

    @GetMapping("/test")
    public String test() {
        return "This is just a test";
    }
}