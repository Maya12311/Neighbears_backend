package com.example.neighbears.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {



   // @GetMapping("/login")
    public String displayLoginPage() {
        System.out.println("workiWorkiiiii");
        // String errorMessage = null;
        //if(null!= error){
        //errorMessage ="Username or Password is incorrect";
        //}
        return "login";
    }
}
