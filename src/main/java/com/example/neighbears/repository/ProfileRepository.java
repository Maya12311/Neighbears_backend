package com.example.neighbears.repository;

import com.example.neighbears.model.Customer;
import com.example.neighbears.model.SelfDescription;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<SelfDescription, Long> {
Optional<SelfDescription> findByCustomerId(Long customerId);
}
