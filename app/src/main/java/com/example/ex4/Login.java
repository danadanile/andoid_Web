package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

//    private String username;
//    private String password;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        handleLogin();

        NavigateToRegister();
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
                public void onSuccess(Object response) {
                    token = (String) response;
                    Intent intent = new Intent(getApplicationContext(), Contacts.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                }

                @Override
                public void onFailure(String error) {
                    TextView errorElement = findViewById(R.id.error);
                    errorElement.setText(error);
                }
            });
        });
    }
}