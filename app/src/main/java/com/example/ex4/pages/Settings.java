package com.example.ex4.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ex4.BaseUrlManager;
import com.example.ex4.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Settings extends AppCompatActivity implements View.OnClickListener {
    private int selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Find the TextView in the layout
        TextView addressTextView = findViewById(R.id.address);

        // Get the singleton instance of BaseUrlManager
        BaseUrlManager baseUrlManager = BaseUrlManager.getInstance();

        // Access the current base URL
        String currentBaseUrl = baseUrlManager.getBaseUrl();

        // Set the base URL text in the TextView
        addressTextView.setText(currentBaseUrl);

        //add button save
        Button btnBlue = findViewById(R.id.blueButton);
        Button btnRed = findViewById(R.id.redButton);
        Button btnGreen = findViewById(R.id.pinkButton);

        btnBlue.setOnClickListener(this);
        btnRed.setOnClickListener(this);
        btnGreen.setOnClickListener(this);

        setSelectedColorAndFrame();
        handleSave();
        handleClose();
    }

    private void handleSave() {
        EditText addressServer = findViewById(R.id.address);

        Button btnSave = findViewById(R.id.saveButton);
        btnSave.setOnClickListener(view -> {
            // Get the singleton instance of BaseUrlManager
            BaseUrlManager baseUrlManager = BaseUrlManager.getInstance();

            // Update the base URL
            String newBaseUrl = String.valueOf(addressServer.getText());

            // Check if the url isn't changed
            if (baseUrlManager.getBaseUrl().equals(newBaseUrl)) {
                Intent intent = new Intent();
                intent.putExtra("selectedColor", selectedColor);
                setResult(Activity.RESULT_OK, intent);

                // Finish the current Settings activity and navigate back to the previous page
                finish();
            } else {
                baseUrlManager.setBaseUrl(newBaseUrl);

                // Create an Intent to return to the Login page
                Intent intent = new Intent(this, Login.class);
                intent.putExtra("selectedColor", selectedColor);
                startActivity(intent);
            }
        });
    }

    private void handleClose() {
        FloatingActionButton btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.putExtra("selectedColor", selectedColor);
            setResult(Activity.RESULT_OK, intent);

            // Finish the current Settings activity and navigate back to the previous page
            finish();
        });
    }

    @Override
    public void onClick(View view) {
        int color = 0;

        // Determine which color button was clicked
        if (view.getId() == R.id.blueButton) {
            color = getResources().getColor(R.color.blue_background);
        } else if (view.getId() == R.id.redButton) {
            color = getResources().getColor(R.color.purple_background);
        } else if (view.getId() == R.id.pinkButton) {
            color = getResources().getColor(R.color.default_background);
        }

        selectedColor = color;
    }

    private void setEditTextBackground(int editTextId, int drawableId) {
        EditText editText = findViewById(editTextId);
        Drawable drawable = getResources().getDrawable(drawableId);
        editText.setBackground(drawable);
    }

    // Call this method to change the background drawable of the username EditText
    private void setFrameEditTextBackground(int drawableId) {
        setEditTextBackground(R.id.address, drawableId);
    }

    private void setButtonAndTextColors(int colorResId) {
        int color = getResources().getColor(colorResId);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setBackgroundTintList(ColorStateList.valueOf(color));

        TextView settingText = findViewById(R.id.settings);
        settingText.setTextColor(color);

        TextView serverText = findViewById(R.id.server);
        serverText.setTextColor(color);

        TextView colorsText = findViewById(R.id.colors);
        colorsText.setTextColor(color);
    }

    private void setSelectedColorAndFrame() {
        // Retrieve the selected color from the intent
        Intent intent = getIntent();
        int selectedColor = intent.getIntExtra("selectedColor", 0);

        if (selectedColor != 0) {
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