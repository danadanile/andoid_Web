package com.example.ex4.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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
import com.example.ex4.MessageViewModel;
import com.example.ex4.MyApplication;
import com.example.ex4.R;
import com.example.ex4.api.ChatAPI;
import com.example.ex4.db.Db;
import com.example.ex4.schemas.Chat;
import com.example.ex4.schemas.Contact;
import com.example.ex4.schemas.Message;
import com.example.ex4.schemas.Msg;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class ChatPage extends AppCompatActivity {
    private Db db;
    private int id;
    private int selectedColor;
    private String message;
    private Chat chat;
    private MessageViewModel messageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        db = new Db(getApplicationContext());

        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        Intent intent = getIntent();
        selectedColor = intent.getIntExtra("selectedColor", 0);

        setSelectedColorAndFrame();

        displayContactInfo();
        handleAddMessage();
        NavigateToContacts();
        getChat();
        getMessagesChat();

        messageViewModel.getMessagesLiveData().observe(this, messages -> {
            // Update your UI components with the new list of messages
            ListView listView = findViewById(R.id.lstMessages);
            final MessageAdapter adapter = new MessageAdapter(ChatPage.this, messages);
            listView.setAdapter(adapter);
        });
    }

    private void getChat() {
        ChatAPI chatAPI = new ChatAPI();
        chatAPI.getChat(MyApplication.getToken(), getId(), status -> {
            if (status) {
                new Thread(() -> {
                    chat = chatAPI.getChat();
                    db.setChatDb(chat);
                }).start();
            }
        });
    }

    private void getMessagesChat() {
        ChatAPI chatAPI = new ChatAPI();
        chatAPI.getMessages(MyApplication.getToken(), getId(), status -> {
            if (status) {
                List<Message> messages = chatAPI.getMessages();

                new Thread(() -> {
                    // Update all the messages of a specific chat in the database
                    Message[] messageArray = messages.toArray(new Message[0]);
                    db.setMessagesDb(messageArray, getId());
                }).start();

                messageViewModel.setMessages(messages);
            }
        });
    }


    private void NavigateToContacts() {
        FloatingActionButton bthRegister = findViewById(R.id.btnExitChat);
        bthRegister.setOnClickListener(view -> {
            Intent intent = new Intent(this, Contacts.class);
            intent.putExtra("selectedColor", selectedColor);
            startActivity(intent);
        });
    }

    private void handleAddMessage() {
        Button bthAdd = findViewById(R.id.sendButton);
        bthAdd.setOnClickListener(view -> {
            EditText message = findViewById(R.id.msgInput);
            String messageText = message.getText().toString();

            if (!messageText.isEmpty()) {
                setMessage(messageText);
                Msg msg = new Msg(getMessage());

                new Thread(() -> {
                    ChatAPI chatAPI = new ChatAPI();
                    chatAPI.addMessage(MyApplication.getToken(), getId(), msg, status -> {
                        if (status) {
                            Message tempMsg = chatAPI.getMessage();
                            runOnUiThread(() -> {
                                // Update the database on the main thread
                                db.setMessageDb(tempMsg, getId());
                                Message[] messages = db.getMessagesDb(getId());
                                List<Message> messageList = Arrays.asList(messages);
                                messageViewModel.setMessages(messageList);
                            });
                            message.setText("");
                        }
                    });
                }).start();
            }
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

    private void setEditTextBackground(int editTextId, int drawableId) {
        EditText editText = findViewById(editTextId);
        Drawable drawable = getResources().getDrawable(drawableId);
        editText.setBackground(drawable);
    }

    private void setImageFrameBackground(int drawableId) {
        ImageView imageFrame = findViewById(R.id.contact_profile_background);
        Drawable drawable = getResources().getDrawable(drawableId);
        imageFrame.setImageDrawable(drawable);
    }

    private void setFrameEditTextBackground(int drawableId) {
        setEditTextBackground(R.id.msgInput, drawableId);
    }

    private void setButtonAndTextColors(int colorResId) {
        int color = getResources().getColor(colorResId);

        Button send = findViewById(R.id.sendButton);
        send.setBackgroundTintList(ColorStateList.valueOf(color));

        View line = findViewById(R.id.line);
        line.setBackgroundTintList(ColorStateList.valueOf(color));

        FloatingActionButton logout = findViewById(R.id.btnExitChat);
        logout.setBackgroundTintList(null);
        logout.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    private void setSelectedColorAndFrame() {
        LinearLayout rootLayout = findViewById(R.id.rootLayout);
        // Set the background color
        rootLayout.setBackgroundColor(selectedColor);

        int defaultColor = getResources().getColor(R.color.default_background);
        int purpleColor = getResources().getColor(R.color.purple_background);
        int blueColor = getResources().getColor(R.color.blue_background);

        if (selectedColor != 0) {
            if (selectedColor == blueColor) {
                setFrameEditTextBackground(R.drawable.blue_frame);
                setImageFrameBackground(R.drawable.image_blue);
                setButtonAndTextColors(R.color.blue);
            } else if (selectedColor == defaultColor) {
                setFrameEditTextBackground(R.drawable.pink_frame);
                setImageFrameBackground(R.drawable.image_pink);
                setButtonAndTextColors(R.color.default_color);
            } else if (selectedColor == purpleColor) {
                setFrameEditTextBackground(R.drawable.purple_frame);
                setImageFrameBackground(R.drawable.image_purple);
                setButtonAndTextColors(R.color.purple);
            }
        }
            else {
                if (selectedColor == 0) {
                    // Set the background color
                    rootLayout.setBackgroundColor(defaultColor);
                }
                setButtonAndTextColors(R.color.default_color);
            }
        }
    }
}
