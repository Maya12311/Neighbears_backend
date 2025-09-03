package com.example.neighbears.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Address {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    @Column(name="house_Number")
    private String houseNumber;
    @Column(name="zip_Code")
    private String zipCode;
    private String city;

    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    private Set<Customer> customers = new HashSet<>();

    public Address(Long id, String street, String houseNumber, String zipCode, String city, Set<Customer> customers) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.customers = customers ;
    }

    public Address(Long id, String street, String houseNumber, String zipCode, String city) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.city = city;
    }

    public Address() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
