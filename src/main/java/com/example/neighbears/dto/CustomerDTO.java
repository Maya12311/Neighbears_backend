package com.example.neighbears.dto;
import com.example.neighbears.model.Authority;
import com.example.neighbears.model.Image;
import com.example.neighbears.model.SelfDescription;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

public class CustomerDTO {

    private long id;
    private String name;
    @Email
    @NotBlank
    private String email;
    private long mobileNumber;
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pwd;
    @NotBlank
    private String role;

    private SelfDescriptionDTO selfDescriptionDTO;

    private Set<Authority> authorities;

    private Image avatar;

    public CustomerDTO() {
    }

    public CustomerDTO(long id, String name, String email, long mobileNumber, String pwd, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.pwd = pwd;
        this.role = role;
    }

    public CustomerDTO(long id, String name, String email, long mobileNumber, String pwd, String role, SelfDescriptionDTO selfDescriptionDTO, Set<Authority> authorities) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.pwd = pwd;
        this.role = role;
        this.selfDescriptionDTO = selfDescriptionDTO;
        this.authorities = authorities;
    }

    public CustomerDTO(long id, String name, String email, long mobileNumber, String pwd, String role, SelfDescriptionDTO selfDescriptionDTO, Set<Authority> authorities, Image avatar) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.pwd = pwd;
        this.role = role;
        this.selfDescriptionDTO = selfDescriptionDTO;
        this.authorities = authorities;
        this.avatar = avatar;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public SelfDescriptionDTO getSelfDescriptionDTO() {
        return selfDescriptionDTO;
    }

    public void setSelfDescriptionDTO(SelfDescriptionDTO selfDescriptionDTO) {
        this.selfDescriptionDTO = selfDescriptionDTO;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
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

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber=" + mobileNumber +
                ", pwd='" + pwd + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
