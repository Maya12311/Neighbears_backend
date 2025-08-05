package com.example.neighbears.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Entity
@Table(name="user_description")
public class SelfDescription {

    @Id
    @Column(name="customer_id")
    private Long customerId;

    private String title;
    private String message;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @OneToOne
    @MapsId
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public SelfDescription(Long customerId, String title, String message, LocalDateTime createdAt, LocalDateTime updatedAt, Customer customer) {
        this.customerId = customerId;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.customer = customer;
    }

    public SelfDescription(Long customerId, String title, String message, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.customerId = customerId;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public SelfDescription() {
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
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


}
