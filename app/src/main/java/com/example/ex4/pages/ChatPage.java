package com.example.ex4.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ex4.MyApplication;
import com.example.ex4.R;
import com.example.ex4.api.ChatAPI;
import com.example.ex4.api.ICallback;
import com.example.ex4.schemas.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class ChatPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        String contactJson = getIntent().getStringExtra("contact");

        // Convert the JSON string back to a Contact object using Gson
        Gson gson = new Gson();
        Contact contact = gson.fromJson(contactJson, Contact.class);

        // Set the image and display name
        ImageView contactImage = findViewById(R.id.contact_profile_img);
        TextView contactName = findViewById(R.id.contact_name);

        Picasso.get().load(contact.getUser().getProfilePic()).into(contactImage);
        contactName.setText(contact.getUser().getDisplayName());

        NavigateToContacts();
        getMessagesChat();
    }

    private void getMessagesChat() {

        ChatAPI chatAPI = new ChatAPI();

        chatAPI.getMessages(MyApplication.getToken(), id, new ICallback() {
            @Override
            public void status(boolean status) {
                if(status) {
                    //finish();
                } else {
                    String error = chatAPI.getError();
                    TextView errorElement = findViewById(R.id.error);
                    errorElement.setText(error);
                }
            }
        });

    }

    private void NavigateToContacts() {
        FloatingActionButton bthRegister = findViewById(R.id.btnExitChat);
        bthRegister.setOnClickListener(view -> {
            Intent intent = new Intent(this, Contacts.class);
            startActivity(intent);
        });
    }

    private void displayContactInfo() {
        TextView contactName = findViewById(R.id.contact_name);
        ImageView contactProfileImg = findViewById(R.id.contact_profile_img);

        Intent intent = getIntent();
        if (intent != null) {
            String username = intent.getStringExtra("username");
            int photoRes = intent.getIntExtra("photo", 0);

            contactName.setText(username);
            contactProfileImg.setImageResource(photoRes);
        }
    }


    ///////////////////////////////////////////////////////////////check
//    @Override
//    protected void onResume() {
//        super.onResume();
//        // Call getMessagesChat() method again when the activity resumes
//        getMessagesChat();
//    }
}
