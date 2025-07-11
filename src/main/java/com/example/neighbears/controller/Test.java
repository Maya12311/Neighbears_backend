package com.example.neighbears.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {


    @GetMapping("/test")
    public String getMyTest(){
        return "this is my test";
    }
}
