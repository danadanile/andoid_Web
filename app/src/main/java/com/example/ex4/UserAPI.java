package com.example.ex4;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {

    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
//    User user = new User();


    public UserAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void createUser(User user) {
//        User user = new User("sha", "1", "sha", "");
        Call<User> call = webServiceAPI.createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int statusCode = response.code();
                if (response.isSuccessful()) {
                    //User createdUser = response.body();
                } else if (statusCode == 400) {
//                    const body = response.body();
//                    const errorMsg = body.error;
//                    setErrorMsg(errorMsg);
//                    setError(true);
                } else if (statusCode == 409) {

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Handle the failure
            }
        });
    }

    public void get(String username) {
        Call<User> call = webServiceAPI.getUser(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }
}


