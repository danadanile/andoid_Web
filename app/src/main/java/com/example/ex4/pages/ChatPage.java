package com.example.ex4.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ex4.MessageAdapter;
import com.example.ex4.MyApplication;
import com.example.ex4.R;
import com.example.ex4.api.ChatAPI;
import com.example.ex4.api.ICallback;
import com.example.ex4.schemas.Contact;
import com.example.ex4.schemas.Message;
import com.example.ex4.schemas.Msg;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.List;

public class ChatPage extends AppCompatActivity {
    private int id;

    private int selectedColor;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        selectedColor = intent.getIntExtra("selectedColor", 0);

        setSelectedColorAndFrame();
        displayContactInfo();
        handleAddMessage();
        NavigateToContacts();
        getMessagesChat();
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



    private void setEditTextBackground(int editTextId, int drawableId) {
        EditText editText = findViewById(editTextId);
        Drawable drawable = getResources().getDrawable(drawableId);
        editText.setBackground(drawable);
    }

    // Call this method to change the background drawable of the username EditText
    private void setFrameEditTextBackground(int drawableId) {
        setEditTextBackground(R.id.msgInput, drawableId);
        //setEditTextBackground(R.id.contact_profile_img, drawableId);
    }

    private void setButtonAndTextColors(int colorResId) {
        int color = getResources().getColor(colorResId);

        Button send = findViewById(R.id.sendButton);
        send.setBackgroundTintList(ColorStateList.valueOf(color));

        View line = findViewById(R.id.line);
        line.setBackgroundTintList(ColorStateList.valueOf(color));

        FloatingActionButton logout = findViewById(R.id.btnExitChat);
        logout.setBackgroundTintList(null);  // Clear the previous background tint
        logout.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    private void setSelectedColorAndFrame() {
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
     else {
        setButtonAndTextColors(R.color.default_color);
    }
    }
}
