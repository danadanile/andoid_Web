package com.example.ex4.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ex4.MyApplication;
import com.example.ex4.R;
import com.example.ex4.api.ChatAPI;
import com.example.ex4.api.ICallback;
import com.example.ex4.schemas.Username;

public class AddContact extends AppCompatActivity {
    private String contactUsername;

    public void setContactUsername(String contactUsername) {
        this.contactUsername = contactUsername;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

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

            chatAPI.addContact(MyApplication.getToken(), username, new ICallback() {
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