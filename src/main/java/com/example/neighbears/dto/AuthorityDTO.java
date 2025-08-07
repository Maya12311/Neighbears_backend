package com.example.neighbears.dto;

import com.example.neighbears.model.Customer;
import jakarta.persistence.*;

public class AuthorityDTO {


        private long id;

        private String name;

        private Customer customer;


    public AuthorityDTO(long id, String name, Customer customer) {
        this.id = id;
        this.name = name;
        this.customer = customer;
    }


    public AuthorityDTO() {
    }



    public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

}
