package com.example.neighbears.repository;

import com.example.neighbears.model.Customer;
import com.example.neighbears.model.SelfDescription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository< Customer, Long> {

Optional<Customer> findByEmail(String email);
//Optional<SelfDescription> findBySelfDescription(String email);
List<Customer> findByAddressId(Long id);

    @Query("""
    SELECT c FROM Customer c
    JOIN FETCH c.address
    LEFT JOIN FETCH c.description
    LEFT JOIN FETCH c.avatar
    WHERE c.address.id = :addressId
""")
    List<Customer> findByAddressIdWithDetails(@Param("addressId") Long addressId);
}
