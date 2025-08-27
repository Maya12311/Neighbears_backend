package com.example.neighbears.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.time.Instant;

@Entity
@Table(name = "customer_avatar")
public class Image {

    @Id
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @MapsId
    @JoinColumn(name="customer_id")
    private Customer customer;

    private String filename;
    private String contentType;
    private Long sizeBytes;
    private Integer widthPx;
    private Integer heightPx;
    private String sha256Hex;
    private String storageKey;
    private Instant uploadedAt;

    public Image() {
    }

    public Image(Customer customer, String filename, String contentType, Long sizeBytes, Integer widthPx, Integer heightPx, String sha256Hex, String storageKey, Instant uploadedAt) {
        this.customer = customer;
        this.filename = filename;
        this.contentType = contentType;
        this.sizeBytes = sizeBytes;
        this.widthPx = widthPx;
        this.heightPx = heightPx;
        this.sha256Hex = sha256Hex;
        this.storageKey = storageKey;
        this.uploadedAt = uploadedAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(Long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public Integer getWidthPx() {
        return widthPx;
    }

    public void setWidthPx(Integer widthPx) {
        this.widthPx = widthPx;
    }

    public Integer getHeightPx() {
        return heightPx;
    }

    public void setHeightPx(Integer heightPx) {
        this.heightPx = heightPx;
    }

    public String getSha256Hex() {
        return sha256Hex;
    }

    public void setSha256Hex(String sha256Hex) {
        this.sha256Hex = sha256Hex;
    }

    public String getStorageKey() {
        return storageKey;
    }

    public void setStorageKey(String storageKey) {
        this.storageKey = storageKey;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
