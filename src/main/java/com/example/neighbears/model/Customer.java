package com.example.neighbears.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    @Column(name = "mobile_number")
    private Long mobileNumber;
    private String pwd;
    private String role;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private SelfDescription description;

    public Customer() {
        super();
    }



    public Customer(long id, String name, String email,Long mobileNumber, String pwd, String role) {
        this.id = id;
        this.email = email;
        this.pwd = pwd;
        this.role = role;
        this.name = name;
        this.mobileNumber = mobileNumber;
    }

    public SelfDescription getDescription() {
        return description;
    }

    public void setDescription(SelfDescription description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals(email, customer.email) && Objects.equals(pwd, customer.pwd) && Objects.equals(role, customer.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, pwd, role);
    }
}
