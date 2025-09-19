package com.rayyan.backend.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/home")
    public String sayHello() {
        return "Hello, world!";
    }

//    @GetMapping("/api/users/login")
//    public String LoginPage() {
//        return "Login Page Works!";
//    }
//
//    @GetMapping("/api/users/register")
//    public String register() {
//        return "Register Page Works!";
//    }
}

