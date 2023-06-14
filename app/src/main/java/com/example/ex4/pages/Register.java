package com.example.ex4.pages;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ex4.api.ICallback;
import com.example.ex4.R;
import com.example.ex4.schemas.User;
import com.example.ex4.api.UserAPI;

public class Register extends AppCompatActivity {
    private final int GALLERY_REQ_CODE = 1000;
    private ImageView imgGallery;
    private String base64Image;

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

                        if (data != null && data.getData() != null) {
                            imgGallery.setImageURI(data.getData());
                        } else {
                            Log.e("RegisterActivity", "Invalid data returned from gallery");
                        }

                //imgGallery.setImageURI(data.getData());

//                Uri imageUri = data.getData();
//
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//                    imgGallery.setImageBitmap(bitmap);
//
//                    // Convert the bitmap to a base64 string
//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//                    byte[] byteArray = byteArrayOutputStream.toByteArray();
//                    base64Image = "data:image/jpeg;base64," + Base64.encodeToString(byteArray, Base64.DEFAULT);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

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
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        });
    }

    private void handleRegister() {
        Button bthRegister = findViewById(R.id.registerButton);
        bthRegister.setOnClickListener(view -> {
            TextView errorElement = findViewById(R.id.error);
            errorElement.setText("");
            EditText usernameInput = findViewById(R.id.username);
            String username = usernameInput.getText().toString();

            EditText passwordInput = findViewById(R.id.password);
            String password = passwordInput.getText().toString();

            EditText confirmPassInput = findViewById(R.id.confirm_password);
            String confirmPassword = confirmPassInput.getText().toString();

            EditText displayNameInput = findViewById(R.id.display_name);
            String displayName = displayNameInput.getText().toString();

            // Create a new User object with the entered data
            //   User user = new User(username, password, displayName, base64Image);
             //System.out.println(base64Image);
            String image = "data:image/jpeg;base64,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";
            User user = new User(username, password, displayName, image);

            UserAPI userAPI = new UserAPI();

            userAPI.createUser(user, new ICallback() {
                @Override
                public void status(boolean status) {
                    if(status) {
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    } else {
                        String error = "Invalid " + userAPI.getError();
                        if (!confirmPassword.equals(password)) {
                        error += ", confirm password ";
                         }
                        TextView errorElement = findViewById(R.id.error);
                        errorElement.setText(error);
                    }
                }
            });
        });
    }
}

