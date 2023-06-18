package com.example.ex4.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ex4.MyApplication;
import com.example.ex4.R;
import com.example.ex4.schemas.Contact;
import com.example.ex4.schemas.Message;
import com.example.ex4.schemas.Msg;
import com.example.ex4.schemas.Username;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatAPI {
        private String error;
        private List<Contact> contactList;
        private List<Message> messages;

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
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
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
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                callback.status(false);
            }
        });
    }


    public void getChats(String token, ICallback callback) {
        Call<List<Contact>> call = webServiceAPI.getChats(token);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(@NonNull Call<List<Contact>> call, @NonNull Response<List<Contact>> response) {
                Log.d("Response", "Response Code: " + response.code()); // Log the response code
                if (response.isSuccessful()) {
                    setContactList(response.body());
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
            public void onFailure(@NonNull Call<List<Contact>> call, @NonNull Throwable t) {
                callback.status(false);
            }
        });
    }

    public void getMessages(String token, int id, ICallback callback) {
        Call<List<Message>> call = webServiceAPI.getMessages(token, id);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(@NonNull Call<List<Message>> call, @NonNull Response<List<Message>> response) {
                if (response.code() == 200) {
                    setMessages(response.body());
                    callback.status(true);
                } else {
                    try {
                        String errorBodyString = response.errorBody().string();
                        JsonObject errorJson = JsonParser.parseString(errorBodyString).getAsJsonObject();
                        String errorMsg = errorJson.get("error").getAsString();
                        setError(errorMsg);
                        callback.status(false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Message>> call, @NonNull Throwable t) {
                callback.status(false);
            }
        });
    }


    public void addMessage(String token, int id, Msg message, ICallback callback) {
        Call<Void> call = webServiceAPI.addMessage(token, id, message);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
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
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                callback.status(false);
            }
        });
    }


    public void setError(String error) {
        this.error = error;
    }
    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getError() {
        return error;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
