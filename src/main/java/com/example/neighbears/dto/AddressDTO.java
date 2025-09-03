package com.example.neighbears.dto;

import java.util.Set;

public class AddressDTO {

private Long id;
private String street;
private String houseNumber;
private String zipCode;
private String city;
private Set<CustomerDTO> customerDTOS;

    public AddressDTO(Long id, String street, String houseNumber, String zipCode, String city) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.city = city;
    }

    public AddressDTO(Long id, String street, String houseNumber, String zipCode, String city, Set<CustomerDTO> customerDTOS) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.customerDTOS = customerDTOS;
    }

    public AddressDTO() {
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
