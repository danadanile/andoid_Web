package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Navigate to the register page
        Button bthRegister = findViewById(R.id.registerButton);
        bthRegister.setOnClickListener(view -> {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        });
    }
}