package com.example.ex4.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.ex4.Contacts;
import com.example.ex4.R;

public class ChatPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        NavigateToContacts();
    }

    private void NavigateToContacts() {
        Button bthRegister = findViewById(R.id.btnExitChat);
        bthRegister.setOnClickListener(view -> {
            Intent intent = new Intent(this, Contacts.class);
            startActivity(intent);
        });
    }
}
