package com.example.ex4.pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.ex4.R;
import com.example.ex4.schemas.User;
import com.example.ex4.api.UserAPI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Register extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imgGallery;
    private int selectedColor;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        selectedColor = intent.getIntExtra("selectedColor", 0);
        imgGallery = (ImageView) findViewById(R.id.image);

        setSelectedColorAndFrame();
        showFileChooser();
        handleRegister();
        navigateToLogin();
    }

    private void navigateToLogin() {
        Button bthLogin = findViewById(R.id.loginButton);
        bthLogin.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            intent.putExtra("selectedColor", selectedColor);
            startActivity(intent);
        });
    }

    private void showFileChooser() {
        Button bthGallery = findViewById(R.id.gallery);

        // Show the image that the user uploads
        bthGallery.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                //Bitmap to get image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgGallery.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String convertToBase64() {
        String base64String = "";

        try {
            @SuppressLint("Recycle") InputStream inputStream = getContentResolver().openInputStream(filePath);

            // Resize the image
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);

            inputStream.close(); // Close the stream to reset its position
            inputStream = getContentResolver().openInputStream(filePath); // Reopen the stream

            int imageWidth = options.outWidth;
            int imageHeight = options.outHeight;
            int desiredWidth = 200;
            int desiredHeight = 150;

            options.inSampleSize = Math.min(imageWidth / desiredWidth, imageHeight / desiredHeight);
            options.inJustDecodeBounds = false;

            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            base64String = "data:image/png;base64," + Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return base64String;
    }

    private void handleRegister() {
        Button bthRegister = findViewById(R.id.registerButton);
        bthRegister.setOnClickListener(view -> {
            EditText usernameInput = findViewById(R.id.username);
            String username = usernameInput.getText().toString();

            EditText passwordInput = findViewById(R.id.password);
            String password = passwordInput.getText().toString();

            EditText confirmPassInput = findViewById(R.id.confirm_password);
            String confirmPassword = confirmPassInput.getText().toString();

            EditText displayNameInput = findViewById(R.id.display_name);
            String displayName = displayNameInput.getText().toString();

            String image = convertToBase64();

            User user = new User(username, password, displayName, image);

            UserAPI userAPI = new UserAPI();
            userAPI.createUser(user, status -> {
                if (status) {
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                } else {
                    String error = userAPI.getError();
                    if (!error.contains("Username")) {
                        error = "Invalid " + error;

                        if (!confirmPassword.equals(password)) {
                            error += ", confirm password ";
                        }
                    }
                    TextView errorElement = findViewById(R.id.error);
                    errorElement.setText(error);
                }
            });
        });
    }

    private void setEditTextBackground(int editTextId, int drawableId) {
        EditText editText = findViewById(editTextId);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), drawableId, null);
        editText.setBackground(drawable);
    }

    // Call this method to change the background drawable of the username EditText
    private void setFrameEditTextBackground(int drawableId) {
        setEditTextBackground(R.id.username, drawableId);
        setEditTextBackground(R.id.password, drawableId);
        setEditTextBackground(R.id.confirm_password, drawableId);
        setEditTextBackground(R.id.display_name, drawableId);
    }

    private void setButtonAndTextColors(int colorResId) {
        int color = getResources().getColor(colorResId);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setBackgroundTintList(ColorStateList.valueOf(color));

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setBackgroundTintList(ColorStateList.valueOf(color));

        Button galleryButton = findViewById(R.id.gallery);
        galleryButton.setBackgroundTintList(ColorStateList.valueOf(color));

        TextView loginText = findViewById(R.id.registerText);
        loginText.setTextColor(color);
    }

    private void setSelectedColorAndFrame() {
        // Retrieve the selected color from the intent

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

