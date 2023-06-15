package com.example.ex4.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


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

    private void displayContactInfo() {
        TextView contactName = findViewById(R.id.contact_name);
        ImageView contactProfileImg = findViewById(R.id.contact_profile_img);

        Intent intent = getIntent();
        if (intent != null) {
            String username = intent.getStringExtra("username");
            int photoRes = intent.getIntExtra("photo", 0);

            contactName.setText(username);
            contactProfileImg.setImageResource(photoRes);
        }
    }
}
