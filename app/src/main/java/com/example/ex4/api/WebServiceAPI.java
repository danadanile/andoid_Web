package com.example.ex4.api;

import com.example.ex4.schemas.User;
import com.example.ex4.schemas.UserLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
 @GET("Users/{username}")
 Call<User> getUser(@Path("username") String username, @Header("Authorization") String token);

 @POST("Users")
 Call<String> createUser(@Body User user);
 @POST("Tokens")
 Call<String> login(@Body UserLogin userLogin);

 @GET("Chats")
 Call<String> getChats(@Header("Authorization") String token);

 @POST("Chats")
 Call<String> addContact(@Header("Authorization") String token, String username);

 @GET("Chats/{id}/Messages")
 Call<String> getMessages(@Header("Authorization") String token, @Path("id") String selectedId);

 @POST("Chats/{id}/Messages")
 Call<String> addMessage(@Header("Authorization") String token, @Path("id") String selectedId, String message);
}

