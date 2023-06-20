package com.example.ex4.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ex4.MessageAdapter;
import com.example.ex4.MyApplication;
import com.example.ex4.R;
import com.example.ex4.api.ChatAPI;
import com.example.ex4.api.ICallback;
import com.example.ex4.db.AppDB2;
import com.example.ex4.db.ChatDao;
import com.example.ex4.db.ContactDao;
import com.example.ex4.schemas.Chat;
import com.example.ex4.schemas.Contact;
import com.example.ex4.schemas.Message;
import com.example.ex4.schemas.Msg;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.List;

public class ChatPage extends AppCompatActivity {

    private AppDB2 db;
    ChatDao chatDao;
    private int id;
    private String message;
    private Chat chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        displayContactInfo();
        handleAddMessage();
        NavigateToContacts();
        getChat();
        getMessagesChat();

    }

    private void setChatDb(Chat chat) {
        db = Room.databaseBuilder(getApplicationContext(),
                        AppDB2.class, "Foo")
                .allowMainThreadQueries()
                .build();
        chatDao = db.chatDao();

        chatDao.insert(chat);
    }


    private void getChat() {

        ChatAPI chatAPI = new ChatAPI();

        chatAPI.getChat(MyApplication.getToken(), getId(), new ICallback() {
            @Override
            public void status(boolean status) {
                if (status) {
                    chat = chatAPI.getChat();
                    setChatDb(chat);
                }
            }
        });
    }


    private void getMessagesChat() {

        ChatAPI chatAPI = new ChatAPI();

        chatAPI.getMessages(MyApplication.getToken(), getId(), new ICallback() {
            @Override
            public void status(boolean status) {
                if (status) {
                    List<Message> messages = chatAPI.getMessages();
                    ListView listView = findViewById(R.id.lstMessages);
                    final MessageAdapter adapter = new MessageAdapter(messages);
                    listView.setAdapter(adapter);
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
        String contactJson = getIntent().getStringExtra("contact");

        // Convert the JSON string back to a Contact object using Gson
        Gson gson = new Gson();
        Contact contact = gson.fromJson(contactJson, Contact.class);

        // Set the image and display name
        ImageView contactImage = findViewById(R.id.contact_profile_img);
        TextView contactName = findViewById(R.id.contact_name);

        contactName.setText(contact.getUser().getDisplayName());

        String base64String = contact.getUser().getProfilePic();

        // Remove the prefix and line breaks from the base64 string
        base64String = base64String.replace("data:image/png;base64,", "")
                .replaceAll("\\s", "");

        // Convert the base64 string to a byte array
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);

        // Create a Bitmap from the byte array
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        // Set the Bitmap as the image source of the ImageView
        contactImage.setImageBitmap(decodedBitmap);

        setId(contact.getId());
    }

    private void handleAddMessage() {
        Button bthAdd = findViewById(R.id.sendButton);
        bthAdd.setOnClickListener(view -> {

            EditText message = findViewById(R.id.msgInput);
            String messageText = message.getText().toString();

            if (!messageText.isEmpty()) {
                setMessage(messageText);

                Msg msg = new Msg(getMessage());

                ChatAPI chatAPI = new ChatAPI();

                chatAPI.addMessage(MyApplication.getToken(), getId(), msg, new ICallback() {
                    @Override
                    public void status(boolean status) {
                        if (status) {
                            getMessagesChat();
                            message.setText("");
                        }
                    }
                });
            }
        });
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    ///////////////////////////////////////////////////////////////check
//    @Override
//    protected void onResume() {
//        super.onResume();
//        // Call getMessagesChat() method again when the activity resumes
//        getMessagesChat();
//    }
}
