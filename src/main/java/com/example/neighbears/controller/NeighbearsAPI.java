package com.example.neighbears.controller;

import com.example.neighbears.dto.CustomerDTO;
import com.example.neighbears.dto.SelfDescriptionDTO;
import com.example.neighbears.service.ImageService;
import com.example.neighbears.service.NeighbearsUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class NeighbearsAPI {

    @Autowired
    private NeighbearsUserDetailsService neighbearsUserDetailsService;
    @Autowired
    private ImageService imageService;

    @GetMapping(value="/allNeighbears")
    public ResponseEntity<List<CustomerDTO>> getAllNeighbears (Authentication authentication) {
    System.out.println(authentication);
        List<CustomerDTO> neighbearsList = new ArrayList<>();
        String email = authentication.getName();

            neighbearsList = neighbearsUserDetailsService.getAllNeighbears( email);

List newList = new ArrayList();

System.out.println("i got the list ");
        for(CustomerDTO one : neighbearsList){
            if (one.getAvatar() != null &&
                    one.getAvatar().getStorageKey() != null &&
                    !one.getAvatar().getStorageKey().isBlank()) {

                try {
                    System.out.println("I go through the list "+one.getId());

                    byte[] av = imageService.getCurrentUserImage(one.getId());
                    one.getAvatar().setAvatar(av);
                } catch (IOException e) {
                    System.err.println("Fehler beim Laden des Bildes für ID " + one.getId());
                } catch (RuntimeException e) {
                    // Wenn im Service trotzdem kein Bild gefunden wird → einfach überspringen
                    System.out.println("Kein Bild vorhanden für ID " + one.getId());


                }
            }}

        return ResponseEntity.ok(neighbearsList);
    }}

