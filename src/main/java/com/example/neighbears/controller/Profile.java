package com.example.neighbears.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
//@RequestMapping("neighbears")
public class Profile {

    @GetMapping("/profile")
    public String getMyProvile(){
        return "this is my profile";
    }
}
