package com.example.ex4;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.lifecycle.ViewModelProvider;

public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    private static String token;
    private static String myProfile;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        token = "";
    }

    public static String getToken() {
        return token;
    }

    public static String getMyProfile() {
        return myProfile;
    }

    public static void setToken(String newToken) {
        token = newToken;
    }

    public static void setMyProfile(String myProfile) {
        MyApplication.myProfile = myProfile;
    }
}
