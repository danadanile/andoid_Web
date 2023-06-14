package com.example.ex4.api;

public interface ICallback <T>{
    void onSuccess(T response);
    void onFailure(String error);
}
