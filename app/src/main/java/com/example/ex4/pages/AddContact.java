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

import com.example.ex4.R;
import com.example.ex4.api.ChatAPI;
import com.example.ex4.api.ICallback;
import com.example.ex4.schemas.Username;

public class AddContact extends AppCompatActivity {
    private String contactUsername;
    private String token;

    private int selectedColor;

    public void setContactUsername(String contactUsername) {
        this.contactUsername = contactUsername;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Check if the intent has extras
        if (intent != null && intent.getExtras() != null) {
            // Retrieve the value of "token" from the intent extras
            setToken(intent.getExtras().getString("token"));
        }

        selectedColor = intent.getIntExtra("selectedColor", 0);

        setSelectedColorAndFrame();

        handleAdd();
    }

    private void handleAdd() {
        Button bthAdd = findViewById(R.id.addButton);
        bthAdd.setOnClickListener(view -> {
            TextView errorElement = findViewById(R.id.error);
            errorElement.setText("");
            EditText Username = findViewById(R.id.contact_username);
            setContactUsername(Username.getText().toString());

            Username username = new Username(contactUsername);

            ChatAPI chatAPI = new ChatAPI();

            chatAPI.addContact(token, username, new ICallback() {
                @Override
                public void status(boolean status) {
                    if(status) {
                        finish();
                    } else {
                        String error = chatAPI.getError();
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
        setEditTextBackground(R.id.contact_username, drawableId);
    }

    private void setButtonAndTextColors(int colorResId) {
        int color = getResources().getColor(colorResId);

        Button add = findViewById(R.id.addButton);
        add.setBackgroundTintList(ColorStateList.valueOf(color));

        TextView addText = findViewById(R.id.ContactText);
        addText.setTextColor(color);
    }

    private void setSelectedColorAndFrame() {

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