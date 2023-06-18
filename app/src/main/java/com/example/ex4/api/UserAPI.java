package com.example.ex4.api;

import com.example.ex4.BaseUrlManager;
import com.example.ex4.MyApplication;
import com.example.ex4.R;
import com.example.ex4.schemas.User;
import com.example.ex4.schemas.UserLogin;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class UserAPI {

    private String error;
    private String token;

    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public UserAPI() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        // Get the singleton instance of BaseUrlManager
        BaseUrlManager baseUrlManager = BaseUrlManager.getInstance();

        // Access the current base URL
        String currentBaseUrl = baseUrlManager.getBaseUrl();

        retrofit = new Retrofit.Builder()
                .baseUrl(currentBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void createUser(User user, ICallback callback) {
        Call<Void> call = webServiceAPI.createUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    callback.status(true);
                } else {
                    try {
                        String errorBodyString = response.errorBody().string();
                        JsonObject errorJson = JsonParser.parseString(errorBodyString).getAsJsonObject();
                        String errorMsg = errorJson.get("error").getAsString();
                        setError(errorMsg);
                        callback.status(false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.status(false);
            }
        });
    }


    public void get(String username, String token, ICallback callback) {
        Call<User> call = webServiceAPI.getUser(username, token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    callback.status(true);
                } else {
                    try {
                        String errorBodyString = response.errorBody().string();
                        JsonObject errorJson = JsonParser.parseString(errorBodyString).getAsJsonObject();
                        String errorMsg = errorJson.get("error").getAsString();
                        setError(errorMsg);
                        callback.status(false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }

    public void login(UserLogin userLogin, ICallback callback) {
        Call<JsonObject> call = webServiceAPI.login(userLogin);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    JsonObject body = response.body();
                    if (body != null && body.has("token")) {
                        String token = body.get("token").getAsString();
                        setToken(token);
                        callback.status(true);
                    }
                } else {
                    try {
                        String errorBodyString = response.errorBody().string();
                        JsonObject errorJson = JsonParser.parseString(errorBodyString).getAsJsonObject();
                        String errorMsg = errorJson.get("error").getAsString();
                        setError(errorMsg);
                        callback.status(false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.status(false);
            }

        });
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

