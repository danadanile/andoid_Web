package com.example.ex4.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ex4.R;
import com.example.ex4.api.ChatAPI;
import com.example.ex4.api.ICallback;
import com.example.ex4.schemas.Username;

public class AddContact extends AppCompatActivity {
    private String contactUsername;
    private String token;

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
}