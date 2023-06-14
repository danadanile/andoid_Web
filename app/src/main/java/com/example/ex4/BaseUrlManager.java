package com.example.ex4;

public class BaseUrlManager {
    private static BaseUrlManager instance;
    private String baseUrl;

    private BaseUrlManager() {
        // Initialize the default value of baseUrl
        baseUrl = "http://10.0.2.2:5000/api/";
    }

    public static synchronized BaseUrlManager getInstance() {
        if (instance == null) {
            instance = new BaseUrlManager();
        }
        return instance;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String newBaseUrl) {
        baseUrl = newBaseUrl;
    }
}
