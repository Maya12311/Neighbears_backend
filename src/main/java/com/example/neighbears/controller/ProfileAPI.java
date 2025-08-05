

package com.example.neighbears.controller;

import com.example.neighbears.dto.SelfDescriptionDTO;
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

    @GetMapping("/profile")
    public String getMyProfile() {
        return "this is my profile";
    }

    @PostMapping(value = "/profile")
    public ResponseEntity<SelfDescriptionDTO> addSelfDescription (@RequestBody SelfDescriptionDTO selfDescription, Authentication authentication){
        System.out.println("sending the Meeeesage" + selfDescription.getMessage());
        System.out.println(authentication);

        SelfDescriptionDTO dto = userProfileService.saveDescription(selfDescription, authentication);
        String successMessage = "added user profile at: " + selfDescription.getCreatedAt();
        System.out.println("sending the Meeeesage");
        return new ResponseEntity<SelfDescriptionDTO>(dto , HttpStatus.OK);

    }
}