package com.example.ex4.api;

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

public class UserAPI {

    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public UserAPI() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void createUser(User user, ICallback<String> callback) {
        Call<String> call = webServiceAPI.createUser(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    callback.onSuccess("yes"); // Pass the response object to onSuccess()
                } else {
                    String errorMsg = null;
                    try {
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody != null) {
                            errorMsg = errorBody.string();
                        }
                        callback.onFailure(errorMsg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callback.onFailure(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    public void get(String username, String token, ICallback callback) {
        Call<User> call = webServiceAPI.getUser(username, token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    User user = response.body();
                    callback.onSuccess(user);
                }
                else {
                    callback.onFailure("Failed to login");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }

    public  void login(UserLogin userLogin, ICallback callback) {
        Call<String> call = webServiceAPI.login(userLogin);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    String token = response.body();
                    callback.onSuccess(token);
                }
                else  {
                    String errorMsg = null;
                    try {
                        errorMsg = response.errorBody().string();
                    } catch (IOException e) {
                    }
                    callback.onFailure(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

}

