package com.example.neighbears.repository;

import com.example.neighbears.model.Customer;
import com.example.neighbears.model.SelfDescription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository< Customer, Long> {

Optional<Customer> findByEmail(String email);
//Optional<SelfDescription> findBySelfDescription(String email);

}
