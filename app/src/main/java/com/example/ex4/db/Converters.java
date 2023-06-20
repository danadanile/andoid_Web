package com.example.ex4.db;

import androidx.room.TypeConverter;

import com.example.ex4.schemas.Message;
import com.example.ex4.schemas.UserDetails;
import com.google.gson.Gson;

public class Converters {
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
