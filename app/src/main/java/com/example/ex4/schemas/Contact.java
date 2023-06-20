package com.example.ex4.schemas;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private UserDetails user;
    private Message lastMessage;


    public void setId(int id) {
        this.id = id;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setUser(UserDetails user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public UserDetails getUser() {
        return user;
    }
}
