package com.example.neighbears.repository;

import com.example.neighbears.model.Image;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ImageRepository extends CrudRepository<Image, Long> {
Optional<Image> findFirstByCustomerIdOrderByUploadedAtDesc(Long customerId);
}
