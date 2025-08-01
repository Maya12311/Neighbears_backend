package com.example.neighbears.controller;

import ch.qos.logback.core.CoreConstants;
import com.example.neighbears.config.EazyBankUsernamePwdAuthenticationProvider;
import com.example.neighbears.dto.CustomerDTO;
import com.example.neighbears.model.Customer;
import com.example.neighbears.repository.CustomerRepository;
import com.example.neighbears.service.NeighbearsUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins= "*")
public class UserController {


    @Autowired
    private Environment environment;

private final CustomerRepository customerRepository;
private final PasswordEncoder passwordEncoder;
private final NeighbearsUserDetailsService userDetailsService;
private final EazyBankUsernamePwdAuthenticationProvider eazyBankUsernamePwdAuthenticationProvider;

    public UserController(CustomerRepository customerRepository, PasswordEncoder passwordEncoder, NeighbearsUserDetailsService userDetailsService, EazyBankUsernamePwdAuthenticationProvider eazyBankUsernamePwdAuthenticationProvider) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.eazyBankUsernamePwdAuthenticationProvider = eazyBankUsernamePwdAuthenticationProvider;
    }




    @PostMapping("/register")
    public ResponseEntity <Map<String, String>> registerUser(@RequestBody CustomerDTO customerDTO){
        try{
            System.out.println("seeeeeeee");

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

   @RequestMapping("/user")
    public CustomerDTO getUserDetailsAfterLogin(Authentication authentication) {
        CustomerDTO customerDTO = userDetailsService.getUserByEmail(authentication.getName());



        return customerDTO;
    }
}
