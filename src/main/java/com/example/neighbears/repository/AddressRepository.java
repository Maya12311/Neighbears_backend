package com.example.neighbears.repository;

import com.example.neighbears.model.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address,  Long> {
    @Query("""
    select a from Address a
    where lower(trim(a.street)) = lower(trim(:street))
      and lower(trim(a.houseNumber)) = lower(trim(:house))
      and a.zipCode = :zip
      and lower(trim(a.city)) = lower(trim(:city))
  """)
    Optional<Address> findNormalized(String street, String house, String zip, String city);
}

