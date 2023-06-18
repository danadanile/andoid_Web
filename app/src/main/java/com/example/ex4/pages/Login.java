package com.example.ex4.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ex4.api.ICallback;
import com.example.ex4.R;
import com.example.ex4.api.UserAPI;
import com.example.ex4.schemas.UserLogin;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Login extends AppCompatActivity {
    private String token;
    private int selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Intent intent = getIntent();
        selectedColor = intent.getIntExtra("selectedColor", 0);

        setSelectedColorAndFrame();

        handleLogin();
        handleSettings();

        NavigateToRegister();
        //NavigateToContacts();
    }


    private void handleSettings() {
        // Navigate to the settings page
        FloatingActionButton btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            intent.putExtra("selectedColor", selectedColor);
            startActivity(intent);
        });
    }

    private void NavigateToRegister() {
        Button bthRegister = findViewById(R.id.registerButton);
        bthRegister.setOnClickListener(view -> {
            Intent intent = new Intent(this, Register.class);
            intent.putExtra("selectedColor", selectedColor);
            startActivity(intent);
        });
    }

    private void NavigateToContacts() {
        Button bthRegister = findViewById(R.id.loginButton);
        bthRegister.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Contacts.class);
            intent.putExtra("selectedColor", selectedColor);
            startActivity(intent);
        });
    }



    private void handleLogin() {
        Button bthLogin = findViewById(R.id.loginButton);
        bthLogin.setOnClickListener(view -> {
            TextView errorElement = findViewById(R.id.error);
            errorElement.setText("");
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

    private void setEditTextBackground(int editTextId, int drawableId) {
        EditText editText = findViewById(editTextId);
        Drawable drawable = getResources().getDrawable(drawableId);
        editText.setBackground(drawable);
    }

    // Call this method to change the background drawable of the username EditText
    private void setFrameEditTextBackground(int drawableId) {
        setEditTextBackground(R.id.username, drawableId);
        setEditTextBackground(R.id.password, drawableId);
    }

    private void setButtonAndTextColors(int colorResId) {
        int color = getResources().getColor(colorResId);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setBackgroundTintList(ColorStateList.valueOf(color));

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setBackgroundTintList(ColorStateList.valueOf(color));

        TextView loginText = findViewById(R.id.loginText);
        loginText.setTextColor(color);
    }

    private void setSelectedColorAndFrame() {
        // Retrieve the selected color from the intent
//        Intent intent = getIntent();
//        int selectedColor = intent.getIntExtra("selectedColor", 0);

        if (selectedColor != 0) {
            LinearLayout rootLayout = findViewById(R.id.rootLayout);
            // Set the background color
            rootLayout.setBackgroundColor(selectedColor);

            int defaultColor = getResources().getColor(R.color.default_background);
            int purpleColor = getResources().getColor(R.color.purple_background);
            int blueColor = getResources().getColor(R.color.blue_background);

            if (selectedColor == blueColor) {
                setFrameEditTextBackground(R.drawable.blue_frame);
                setButtonAndTextColors(R.color.blue);
            } else if (selectedColor == defaultColor) {
                setFrameEditTextBackground(R.drawable.pink_frame);
                setButtonAndTextColors(R.color.default_color);
            } else if (selectedColor == purpleColor) {
                setFrameEditTextBackground(R.drawable.purple_frame);
                setButtonAndTextColors(R.color.purple);
            }
        }
    }
}