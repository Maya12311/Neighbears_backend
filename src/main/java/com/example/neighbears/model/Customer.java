package com.example.neighbears.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

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

    @OneToMany(mappedBy = "customer", fetch= FetchType.EAGER)
    @JsonIgnore //if UI is giving error message
    private Set<Authority> authorities;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Image avatar;

    public Customer() {
        super();
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer(long id, String name, String email, Long mobileNumber, String pwd, String role, SelfDescription description, Set<Authority> authorities, Image avatar) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.pwd = pwd;
        this.role = role;
        this.description = description;
        this.authorities = authorities;
        this.avatar = avatar;
    }

    public Customer(long id, String name, String email, Long mobileNumber, String pwd, String role, SelfDescription description, Set<Authority> authorities) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.pwd = pwd;
        this.role = role;
        this.description = description;
        this.authorities = authorities;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public SelfDescription getDescription() {
        return description;
    }

    public void setDescription(SelfDescription description) {
        this.description = description;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
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
