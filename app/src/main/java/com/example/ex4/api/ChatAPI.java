package com.example.ex4.api;

import com.example.ex4.MyApplication;
import com.example.ex4.R;
import com.example.ex4.schemas.Username;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatAPI {
        private String error;

        Retrofit retrofit;
        WebServiceAPI webServiceAPI;

        public ChatAPI() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
            webServiceAPI = retrofit.create(WebServiceAPI.class);
        }


    public void addContact(String token, Username username, ICallback callback) {
        Call<Void> call = webServiceAPI.addContact(token, username);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.status(true);
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBodyString = response.errorBody().string();
                            JsonObject errorJson = JsonParser.parseString(errorBodyString).getAsJsonObject();
                            String errorMsg = errorJson.get("error").getAsString();
                            setError(errorMsg);
                        }
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

//        public void getChats(String token, ICallback callback) {
//            Call<String> call = webServiceAPI.getChats(token);
//            call.enqueue(new Callback<String>() {
//                @Override
//                public void onResponse(Call<String> call, Response<String> response) {
//                    if (response.isSuccessful()) {
////                         errorMsg = null;
////
////                        callback.onFailure(errorMsg);
//
//                    } else {
//                        callback.onSuccess(null);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<String> call, Throwable t) {
//
//                }
//            });
//        }



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


    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
