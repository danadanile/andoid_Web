package com.example.ex4.schemas;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Chat {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private UserDetails[] users;
    private Message[] messages;

    public Chat(int id, UserDetails[] users, Message[] messages) {
        this.id = id;
        this.users = users;
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public UserDetails[] getUsers() {
        return users;
    }

    public Message[] getMessages() {
        return messages;
    }
}
