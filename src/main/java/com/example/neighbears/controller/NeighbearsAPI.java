package com.example.neighbears.controller;

import com.example.neighbears.dto.CustomerDTO;
import com.example.neighbears.dto.SelfDescriptionDTO;
import com.example.neighbears.service.NeighbearsUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class NeighbearsAPI {

    @Autowired
    private NeighbearsUserDetailsService neighbearsUserDetailsService;

    @GetMapping(value="/allNeighbears")
    public ResponseEntity<List<CustomerDTO>> getAllNeighbears (Authentication authentication) {
    System.out.println(authentication);
        List<CustomerDTO> neighbearsList = new ArrayList<>();
        String email = authentication.getName();
        neighbearsList = neighbearsUserDetailsService.getAllNeighbears( email);
        return ResponseEntity.ok(neighbearsList);
    }
}
