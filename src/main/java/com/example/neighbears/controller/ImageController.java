package com.example.neighbears.controller;

import com.example.neighbears.dto.CustomerDTO;
import com.example.neighbears.dto.ImageDTO;
import com.example.neighbears.service.ImageService;
import com.example.neighbears.service.NeighbearsUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ImageController {

    private static final Logger log = LoggerFactory.getLogger(ImageController.class);

    private final ImageService imageService;
    private final NeighbearsUserDetailsService neighbearsUserDetailsService;

    public ImageController(ImageService imageService, NeighbearsUserDetailsService neighbearsUserDetailsService) {
        this.imageService = imageService;
        this.neighbearsUserDetailsService = neighbearsUserDetailsService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/uploadImage")
    public ResponseEntity<ImageDTO> uploadImage(@RequestParam("file")MultipartFile file, Authentication authentication) throws Exception {
        log.info("I wanna know what is written in the file: ", file);
        ImageDTO savedImage = imageService.saveImage(authentication.getName(), file);
        return ResponseEntity.ok(savedImage);
    }

    @GetMapping(value="/getprofilePic")
    public ResponseEntity<byte[]> getCurrentUserImage(Authentication authentication) throws IOException {
        String email = authentication.getName();
        CustomerDTO customerDTO = neighbearsUserDetailsService.getUserByEmail(email);
        byte[] image = imageService.getCurrentUserImage(customerDTO.getId());
        String type = imageService.getCustomerImageType(customerDTO.getId());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(type))
                .body(image);
    }
}
