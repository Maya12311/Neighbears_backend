package com.example.neighbears.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {


    @GetMapping("/test")
    public String getMyTest( Authentication authentication){
//        System.out.println("show iiid"+id);

        Authentication auth = authentication;
        System.out.println("BLU"+authentication.getAuthorities());

        return "this is my test" ;
    }
}
