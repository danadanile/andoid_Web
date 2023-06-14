package com.example.ex4.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ex4.BaseUrlManager;
import com.example.ex4.R;

public class Settings extends AppCompatActivity {

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
            startActivity(intent);
        });
    }
}