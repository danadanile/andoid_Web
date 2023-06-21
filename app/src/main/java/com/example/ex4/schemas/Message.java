package com.example.ex4.schemas;

import androidx.room.Entity;

@Entity
public class Message {
    private int id;
    private String created;
    private Username sender;
    private String content;

    public Message(int id, String created, Username sender, String content) {
        this.id = id;
        this.created = created;
        this.sender = sender;
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public String getContent() {
        return content;
    }

    public Username getSender() {
        return sender;
    }
}
