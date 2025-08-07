package com.example.neighbears.dto;

import java.time.LocalDateTime;

public class SelfDescriptionDTO {

    private Long customerId;
    private String title;
    private String message;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CustomerDTO customer;


    public SelfDescriptionDTO() {
    }

    public SelfDescriptionDTO(Long customerId, String title, String message) {
        this.customerId = customerId;
        this.title = title;
        this.message = message;
    }

    public SelfDescriptionDTO(Long customerId, String title, String message, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.customerId = customerId;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }



    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public Long getCustomerId() {
        return customerId;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }



    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


    @Override
    public String toString() {
        return "SelfDescriptionDTO{" +
                "customerId=" + customerId +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
