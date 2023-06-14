package com.example.ex4.schemas;

public class Chat {

    private int id;
    private UserDetails[] users;
    private Message[] messages;

    public Chat(int id, UserDetails[] users, Message[] messages) {
        this.id = id;
        this.users = users;
        this.messages = messages;
    }

}
