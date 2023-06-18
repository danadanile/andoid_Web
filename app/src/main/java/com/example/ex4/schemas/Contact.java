package com.example.ex4.schemas;

public class Contact {
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
