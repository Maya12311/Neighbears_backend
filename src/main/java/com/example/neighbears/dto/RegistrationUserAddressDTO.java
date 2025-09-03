package com.example.neighbears.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class RegistrationUserAddressDTO {

    @NotNull
    @Valid
private CustomerDTO customerDTO;

@NotNull
@Valid
private AddressDTO addressDTO;

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    @Override
    public String toString() {
        return "RegistrationUserAddressDTO{" +
                "customerDTO=" + customerDTO +
                ", addressDTO=" + addressDTO +
                '}';
    }
}
