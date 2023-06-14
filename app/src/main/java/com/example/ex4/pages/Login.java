package com.example.ex4.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ex4.api.ICallback;
import com.example.ex4.R;
import com.example.ex4.api.UserAPI;
import com.example.ex4.schemas.UserLogin;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Login extends AppCompatActivity {
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        handleLogin();
        handleSettings();

        NavigateToRegister();
    }

    private void handleSettings() {
        // Navigate to the settings page
        FloatingActionButton btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent);
        });
    }

    private void NavigateToRegister() {
        Button bthRegister = findViewById(R.id.registerButton);
        bthRegister.setOnClickListener(view -> {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        });
    }

    private void NavigateToContacts() {
        Button bthRegister = findViewById(R.id.loginButton);
        bthRegister.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Contacts.class);
            startActivity(intent);
        });
    }

    private void handleLogin() {
        Button bthLogin = findViewById(R.id.loginButton);
        bthLogin.setOnClickListener(view -> {
            EditText usernameInput = findViewById(R.id.username);
            String username = usernameInput.getText().toString();

            EditText passwordInput = findViewById(R.id.password);
            String password = passwordInput.getText().toString();

            UserLogin userLogin = new UserLogin(username, password);
            UserAPI userAPI = new UserAPI();

            userAPI.login(userLogin, new ICallback() {
                @Override
                public void status(boolean status) {
                    if(status) {
                        token = "Bearer " + userAPI.getToken();
                        Intent intent = new Intent(getApplicationContext(), Contacts.class);
                        intent.putExtra("token", token);
                        startActivity(intent);
                    } else {
                        String error = userAPI.getError();
                        TextView errorElement = findViewById(R.id.error);
                        errorElement.setText(error);
                    }
                }
            });
        });
    }
}