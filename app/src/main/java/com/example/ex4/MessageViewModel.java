package com.example.ex4;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ex4.schemas.Message;

import java.util.List;

public class MessageViewModel extends ViewModel {
    private MutableLiveData<List<Message>> messagesLiveData;

    public LiveData<List<Message>> getMessagesLiveData() {
        if (messagesLiveData == null) {
            messagesLiveData = new MutableLiveData<>();
            // Initialize the messagesLiveData if needed
        }
        return messagesLiveData;
    }

    public void setMessages(List<Message> messages) {
        if (messagesLiveData != null) {
            messagesLiveData.setValue(messages);
        }
    }
}

