package com.example.ex4;

import android.widget.ImageView;

public class User {
    private String username;
    private String password;
    private String confirmPassword;
    private String displayName;
    private ImageView imgGallery;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setImgGallery(ImageView imgGallery) {
        this.imgGallery = imgGallery;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ImageView getImgGallery() {
        return imgGallery;
    }
}
