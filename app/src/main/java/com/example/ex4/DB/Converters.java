package com.example.ex4.DB;

import androidx.room.TypeConverter;

import com.example.ex4.schemas.Message;
import com.example.ex4.schemas.UserDetails;
import com.google.gson.Gson;

public class Converters {

    @TypeConverter
    public static UserDetails[] fromStringUser(String value) {
        return new Gson().fromJson(value, UserDetails[].class);
    }

    @TypeConverter
    public static String userDetailsArrayToString(UserDetails[] userDetailsArray) {
        return new Gson().toJson(userDetailsArray);
    }

    @TypeConverter
    public static Message[] fromStringMessage(String value) {
        return new Gson().fromJson(value, Message[].class);
    }

    @TypeConverter
    public static String messageArrayToString(Message[] messagesArray) {
        return new Gson().toJson(messagesArray);
    }
}
