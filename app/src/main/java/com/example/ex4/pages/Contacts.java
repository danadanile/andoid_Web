package com.example.ex4.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.ex4.R;
import com.example.ex4.pages.AddContact;
import com.example.ex4.pages.Login;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Contacts extends AppCompatActivity {
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Check if the intent has extras
        if (intent != null && intent.getExtras() != null) {
            // Retrieve the value of "token" from the intent extras
            setToken(intent.getExtras().getString("token"));
        }
            NavigateToAddContact();
        NavigateToLogin();
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Navigate to the add contact page
    private void NavigateToAddContact() {
        FloatingActionButton bthAdd = findViewById(R.id.btnAdd);
        bthAdd.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddContact.class);
            intent.putExtra("token", token);
            startActivity(intent);
        });
    }


    private void NavigateToLogin() {
        FloatingActionButton btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(view -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        });
    }
}
