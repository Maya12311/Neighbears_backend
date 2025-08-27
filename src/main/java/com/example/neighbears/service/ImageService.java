package com.example.neighbears.service;

import com.example.neighbears.model.Image;
import com.example.neighbears.repository.CustomerRepository;
import com.example.neighbears.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.neighbears.service.UserProfileService;

@Service
public class ImageService {

    private final CustomerRepository customerRepository;
    private final ImageRepository imageRepository;
    private final String uploadDir;

    public ImageService(@Value("${app.local.base-dir}") String uploadDir, CustomerRepository customerRepository, ImageRepository imageRepository) {
        this.customerRepository = customerRepository;
        this.imageRepository = imageRepository;
        this.uploadDir = uploadDir;

        //public Image saveImage(Long customerId, MultipartFile file) throws Exception{

     //   }
    }
}
