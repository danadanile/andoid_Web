package com.example.ex4;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    public static Context context;
    private static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String newToken) {
        token = newToken;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        token = "";
    }
}
