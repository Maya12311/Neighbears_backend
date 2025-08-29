package com.example.neighbears.controller;

import com.example.neighbears.dto.ImageDTO;
import com.example.neighbears.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/uploadImage")
    public ResponseEntity<ImageDTO> uploadImage(@RequestParam("file")MultipartFile file, Authentication authentication) throws Exception {

        ImageDTO savedImage = imageService.saveImage(authentication.getName(), file);
        return ResponseEntity.ok(savedImage);
    }
}
