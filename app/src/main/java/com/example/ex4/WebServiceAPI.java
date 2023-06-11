package com.example.ex4;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
 @GET("Users/{username}")
 Call<User> getUser(@Path("username") String username);

 @POST("Users")
 Call<User> createUser(@Body User user);

 }
