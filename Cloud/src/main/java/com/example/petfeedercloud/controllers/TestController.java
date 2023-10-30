package com.example.petfeedercloud.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private static final String template = "Hello, %s!";

//http://localhost:8080/test
    @GetMapping("/test")
    public String test() {
        return "This is just a test";
    }
}