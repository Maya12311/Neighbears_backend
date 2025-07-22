package com.example.neighbears.dto;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CustomerDTO {

    private long id;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String pwd;
    @NotBlank
    private String role;

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

    public CustomerDTO(long id, String email, String pwd, String role) {
        this.id = id;
        this.email = email;
        this.pwd = pwd;
        this.role = role;



    }
}
