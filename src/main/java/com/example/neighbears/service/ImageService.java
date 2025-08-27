package com.example.neighbears.service;

import com.example.neighbears.model.Customer;
import com.example.neighbears.model.Image;
import com.example.neighbears.repository.CustomerRepository;
import com.example.neighbears.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.neighbears.service.UserProfileService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.HexFormat;
import java.util.Optional;

@Service
public class ImageService {

    private final CustomerRepository customerRepository;
    private final ImageRepository imageRepository;
    private final String uploadDir;

    public ImageService(@Value("${app.local.base-dir}") String uploadDir, CustomerRepository customerRepository, ImageRepository imageRepository) {
        this.customerRepository = customerRepository;
        this.imageRepository = imageRepository;
        this.uploadDir = uploadDir;
    }

        public Image saveImage(Long customerId, MultipartFile file) throws Exception{

            Optional<Customer> optional = customerRepository.findById(customerId);
            Customer customer = optional.orElseThrow(()-> new UsernameNotFoundException("The user wasn't found: " + customerId));

            String storageKey = customerId + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String filePath = uploadDir + "/" + storageKey;

            Files.write(Paths.get(filePath), file.getBytes());

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String sha256 = HexFormat.of().formatHex(digest.digest(file.getBytes()));

            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();

            Image img = new Image();
            img.setCustomer(customer);
            img.setFilename(file.getOriginalFilename());
            img.setContentType(file.getContentType());
            img.setSizeBytes(file.getSize());
            img.setWidthPx(width);
            img.setHeightPx(height);
            img.setSha256Hex(sha256);
            img.setStorageKey(storageKey);
            img.setUploadedAt(Instant.now());

            return imageRepository.save(img);


        }
}
