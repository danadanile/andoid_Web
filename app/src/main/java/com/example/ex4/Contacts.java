package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Contacts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        // Navigate to the add contact page
        Button bthAdd = findViewById(R.id.btnAdd);
        bthAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddContact.class);
            startActivity(intent);
        });
    }
}