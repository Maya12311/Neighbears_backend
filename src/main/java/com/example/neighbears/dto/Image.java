package com.example.neighbears.dto;

import javax.validation.constraints.NotNull;
import java.time.Instant;

public class Image {
    @NotNull
    private String filename;
    @NotNull
    private String contentType;
    @NotNull
    private String sizeBytes;
    private Integer widthPx;
    private Integer heighPx;
    @NotNull
    private String sha256Hex;
    @NotNull
    private String storageKey;
    @NotNull
    private Instant uploadedAt;

    public Image() {
    }

    public Image(String filename, String contentType, String sizeBytes, Integer widthPx, Integer heighPx, String sha256Hex, String storageKey, Instant uploadedAt) {
        this.filename = filename;
        this.contentType = contentType;
        this.sizeBytes = sizeBytes;
        this.widthPx = widthPx;
        this.heighPx = heighPx;
        this.sha256Hex = sha256Hex;
        this.storageKey = storageKey;
        this.uploadedAt = uploadedAt;
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

    public String getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(String sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public Integer getWidthPx() {
        return widthPx;
    }

    public void setWidthPx(Integer widthPx) {
        this.widthPx = widthPx;
    }

    public Integer getHeighPx() {
        return heighPx;
    }

    public void setHeighPx(Integer heighPx) {
        this.heighPx = heighPx;
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
