package com.example.ex4.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ex4.R;
import com.example.ex4.pages.AddContact;
import com.example.ex4.pages.Login;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Contacts extends AppCompatActivity {
    private int selectedColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

//        Intent intentColor = getIntent();
//        selectedColor = intentColor.getIntExtra("selectedColor", 0);

       // setSelectedColorAndFrame();

        // Navigate to the add contact page
        FloatingActionButton bthAdd = findViewById(R.id.btnAdd);
        bthAdd.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddContact.class);
            startActivity(intent);
        });
        NavigateToLogin();
    }

    private void NavigateToLogin() {
        Button bthRegister = findViewById(R.id.btnLogout);
        bthRegister.setOnClickListener(view -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);

        });
    }




    private void setButtonAndTextColors(int colorResId) {
        int color = getResources().getColor(colorResId);

        Button loginButton = findViewById(R.id.addButton);
        loginButton.setBackgroundTintList(ColorStateList.valueOf(color));

        Button registerButton = findViewById(R.id.btnLogout);
        registerButton.setBackgroundTintList(ColorStateList.valueOf(color));

    }

    private void setSelectedColorAndFrame() {
        // Retrieve the selected color from the intent
//        Intent intent = getIntent();
//        int selectedColor = intent.getIntExtra("selectedColor", 0);

        if (selectedColor != 0) {

            int defaultColor = getResources().getColor(R.color.default_background);
            int purpleColor = getResources().getColor(R.color.purple_background);
            int blueColor = getResources().getColor(R.color.blue_background);

            if (selectedColor == blueColor) {
                setButtonAndTextColors(R.color.blue);
            } else if (selectedColor == defaultColor) {
                setButtonAndTextColors(R.color.default_color);
            } else if (selectedColor == purpleColor) {
                setButtonAndTextColors(R.color.purple);
            }
        }
    }
//    private void NavigateToChatPage() {
//        Button btnExitChat = findViewById(R.id.);
//        btnExitChat.setOnClickListener(view -> {
//            Intent intent = new Intent(this, ChatPage.class);
//            intent.putExtra("username", );
//            intent.putExtra("photo", R.mipmap.ic_launcher_round); // Replace R.mipmap.ic_launcher_round with the actual photo resource
//            startActivity(intent);
//        });

}
