

package com.example.neighbears.controller;

import com.example.neighbears.dto.SelfDescriptionDTO;
import com.example.neighbears.model.SelfDescription;
import com.example.neighbears.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("neighbears")
public class ProfileAPI {

    @Autowired
    private UserProfileService userProfileService;


    @GetMapping(value="/profile")
    public ResponseEntity<SelfDescriptionDTO> getDescription (Authentication authentication) {
        String email = authentication.getName();
        SelfDescriptionDTO selfDescriptionDTO = userProfileService.getDescription(email);
        if (selfDescriptionDTO == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(selfDescriptionDTO);
    }

    @PostMapping(value = "/profile")
    public ResponseEntity<SelfDescriptionDTO> addSelfDescription (@RequestBody SelfDescriptionDTO selfDescription, Authentication authentication){
        SelfDescriptionDTO dto = userProfileService.saveDescription(selfDescription, authentication);
        String successMessage = "added user profile at: " + selfDescription.getCreatedAt();
        return new ResponseEntity<SelfDescriptionDTO>(dto , HttpStatus.OK);

    }

    @PutMapping(value="/profile")
    public ResponseEntity<SelfDescriptionDTO> changeDescription (@RequestBody SelfDescriptionDTO selfDescriptionDTO, Authentication authentication){
        SelfDescriptionDTO dto = userProfileService.saveDescription(selfDescriptionDTO, authentication);
        String successMessage = "Description changed at" + dto.getUpdatedAt();
        return new ResponseEntity<SelfDescriptionDTO>(dto, HttpStatus.OK);
    }
}