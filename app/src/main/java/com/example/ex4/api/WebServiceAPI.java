package com.example.ex4.api;

import com.example.ex4.schemas.Chat;
import com.example.ex4.schemas.Contact;
import com.example.ex4.schemas.Message;
import com.example.ex4.schemas.Msg;
import com.example.ex4.schemas.Username;
import com.example.ex4.schemas.User;
import com.example.ex4.schemas.UserLogin;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @GET("Users/{username}")
    Call<User> getUser(@Path("username") String username, @Header("authorization") String token);

    @POST("Users")
    Call<Void> createUser(@Body User user);

    @POST("Tokens")
    Call<JsonObject> login(@Body UserLogin userLogin);

    @GET("Chats")
    Call<List<Contact>> getChats(@Header("authorization") String token);

    @POST("Chats")
    Call<JsonObject> addContact(@Header("authorization") String authorization, @Body Username username);

    @GET("Chats/{id}")
    Call<Chat> getChat(@Header("authorization") String token, @Path("id") int selectedId);

    @GET("Chats/{id}/Messages")
    Call<List<Message>> getMessages(@Header("authorization") String token, @Path("id") int selectedId);

    @POST("Chats/{id}/Messages")
    Call<Void> addMessage(@Header("authorization") String token, @Path("id") int selectedId, @Body Msg message);
}

