package com.example.ex4.api;

import com.example.ex4.MyApplication;
import com.example.ex4.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatAPI {

        Retrofit retrofit;
        WebServiceAPI webServiceAPI;

        public ChatAPI() {
            retrofit = new Retrofit.Builder()
                    .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            webServiceAPI = retrofit.create(WebServiceAPI.class);
        }

        public void getChats(String token, ICallback callback) {
            Call<String> call = webServiceAPI.getChats(token);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
//                         errorMsg = null;
//
//                        callback.onFailure(errorMsg);

                    } else {
                        callback.onSuccess(null);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }

//        public void get(String username) {
//            Call<User> call = webServiceAPI.getUser(username);
//            call.enqueue(new Callback<User>() {
//                @Override
//                public void onResponse(Call<User> call, Response<User> response) {
//                    User user = response.body();
//                }
//
//                @Override
//                public void onFailure(Call<User> call, Throwable t) {
//                }
//            });
//        }
}
