package com.example.ex4;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    private final int GALLERY_REQ_CODE = 1000;

    private String username;
    private String password;
    private String confirmPassword;
    private String displayName;
    private ImageView imgGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        showImage();

        handleRegister();

        navigateToLogin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                imgGallery.setImageURI(data.getData());
            }
        }
    }

    private void showImage() {
        imgGallery = findViewById(R.id.image);
        Button bthGallery = findViewById(R.id.gallery);

        // Show the image that the user uploads
        bthGallery.setOnClickListener(v -> {
            Intent iGallery = new Intent(Intent.ACTION_PICK);
            iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(iGallery, GALLERY_REQ_CODE);
        });
    }

    private void navigateToLogin() {
        Button bthLogin = findViewById(R.id.loginButton);
        bthLogin.setOnClickListener(view -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        });
    }

    private void handleRegister() {
        Button bthRegister = findViewById(R.id.registerButton);
        bthRegister.setOnClickListener(view -> {
            EditText usernameInput = findViewById(R.id.username);
            username = usernameInput.getText().toString();

            EditText passwordInput = findViewById(R.id.password);
            password = passwordInput.getText().toString();

            EditText confirmPassInput = findViewById(R.id.confirm_password);
            confirmPassword = confirmPassInput.getText().toString();

            EditText displayNameInput = findViewById(R.id.display_name);
            displayName = displayNameInput.getText().toString();
        });
    }
}