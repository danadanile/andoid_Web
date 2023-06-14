package com.example.ex4.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.ex4.R;

public class AddContact extends AppCompatActivity {
    private String contactUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        handleAdd();
    }

    private void handleAdd() {
        Button bthAdd = findViewById(R.id.addButton);
        bthAdd.setOnClickListener(view -> {
            EditText username = findViewById(R.id.contact_username);
            contactUsername = username.getText().toString();
        });
    }
}