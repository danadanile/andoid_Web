package com.example.ex4.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ex4.BaseUrlManager;
import com.example.ex4.R;

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

        handleSave();
    }

    private void handleSave() {
        EditText addressServer = findViewById(R.id.address);

        Button btnSave = findViewById(R.id.saveButton);
        btnSave.setOnClickListener(view -> {
            // Get the singleton instance of BaseUrlManager
            BaseUrlManager baseUrlManager = BaseUrlManager.getInstance();

            // Update the base URL
            String newBaseUrl = String.valueOf(addressServer.getText());
            baseUrlManager.setBaseUrl(newBaseUrl);


            Intent intent = new Intent(getApplicationContext(), Login.class);
            intent.putExtra("selectedColor", selectedColor);
            startActivity(intent);

        });
    }


    @Override
    public void onClick(View view) {

        // Determine which color button was clicked
        int color = 0;
        if (view.getId() == R.id.blueButton) {
            color = getResources().getColor(R.color.blue);
        } else if (view.getId() == R.id.redButton) {
            color = getResources().getColor(R.color.red);
        } else if (view.getId() == R.id.pinkButton) {
            color = getResources().getColor(R.color.color);
        }
        selectedColor = color;
    }
}