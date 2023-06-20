package com.example.ex4.db;

import androidx.room.TypeConverter;

import com.example.ex4.schemas.Message;
import com.example.ex4.schemas.UserDetails;
import com.google.gson.Gson;

public class ConvertersContacts {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static String userDetailsToJson(UserDetails userDetails) {
        return gson.toJson(userDetails);
    }

    @TypeConverter
    public static UserDetails jsonToUserDetails(String json) {
        return gson.fromJson(json, UserDetails.class);
    }

    @TypeConverter
    public static String messageToJson(Message message) {
        return gson.toJson(message);
    }

    @TypeConverter
    public static Message jsonToMessage(String json) {
        return gson.fromJson(json, Message.class);
    }
}
