package com.example.neighbears.controller;

import ch.qos.logback.core.CoreConstants;
import com.example.neighbears.dto.CustomerDTO;
import com.example.neighbears.model.Customer;
import com.example.neighbears.repository.CustomerRepository;
import com.example.neighbears.service.NeighbearsUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {


    @Autowired
    private Environment environment;

private final CustomerRepository customerRepository;
private final PasswordEncoder passwordEncoder;
private final NeighbearsUserDetailsService userDetailsService;

    public UserController(CustomerRepository customerRepository, PasswordEncoder passwordEncoder, NeighbearsUserDetailsService userDetailsService) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity <Map<String, String>> registerUser(@RequestBody CustomerDTO customerDTO){
        try{
            System.out.println(customerDTO);
        Long customerId = userDetailsService.registerUser(customerDTO);
            if(customerId > 0) {
                String successMessage = environment.getProperty("API.INSERT_SUCCESS") + customerId;
                Map<String, String> response = new HashMap<>();
                response.put("message", successMessage);
                return new ResponseEntity<>(response, HttpStatus.CREATED);

            } else{
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "User registration failed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

        } catch (Exception ex){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "An exception occurred:");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

        }
    }
}
