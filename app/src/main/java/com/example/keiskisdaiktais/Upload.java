package com.example.keiskisdaiktais;

public class Upload {
    private String imageUrl;
    private String description;
    private String price;
    private String userId; // Jei reikalinga

    private String uploadId;

    public Upload() {
        // Tuščias konstruktorius reikalingas Firebaseui
    }

    public Upload(String imageUrl, String description, String price, String userId) {
        this.imageUrl = imageUrl;
        this.description = description;
        this.price = price;
        this.userId = userId;
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}

