package com.example.rxjava2.demoapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping(value = "/demo")
    public String showDemo(){
        return "hello world";
    }

}
