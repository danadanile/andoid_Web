package com.example.ex4.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.ex4.ContactAdapter;
import com.example.ex4.MyApplication;
import com.example.ex4.R;
import com.example.ex4.api.ChatAPI;
import com.example.ex4.api.ICallback;
import com.example.ex4.schemas.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Contacts extends AppCompatActivity {
    private int selectedColor;
    private ListView lstContacts;

    public void setLstContacts(ListView lstContacts) {
        this.lstContacts = lstContacts;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        setLstContacts(findViewById(R.id.lstContacts));

        handleSettings();

//        Intent intentColor = getIntent();
//        selectedColor = intentColor.getIntExtra("selectedColor", 0);
        // setSelectedColorAndFrame();

        NavigateToAddContact();
        NavigateToLogin();
        getContacts();
    }

    // Navigate to the add contact page
    private void NavigateToAddContact() {
        FloatingActionButton bthAdd = findViewById(R.id.btnAdd);
        bthAdd.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddContact.class);
            startActivity(intent);
        });
    }

    private void NavigateToLogin() {
        FloatingActionButton btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(view -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        });
    }

    private void getContacts() {

            ChatAPI chatAPI = new ChatAPI();

            chatAPI.getChats(MyApplication.getToken(), new ICallback() {
                @Override
                public void status(boolean status) {
                    if(status) {
                        List<Contact> contactList =  chatAPI.getContactList();
                        final ContactAdapter contactAdapter = new ContactAdapter(contactList);
                        lstContacts.setAdapter(contactAdapter);
//                        lstContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                Contact contact = contactList.get(position);
//                                //p.select();
//                                contactAdapter.notifyDataSetChanged();
//                            }
//                        });
                    } else {
                    }
                }
            });
    }

    private void setButtonAndTextColors(int colorResId) {
        int color = getResources().getColor(colorResId);

        Button loginButton = findViewById(R.id.addButton);
        loginButton.setBackgroundTintList(ColorStateList.valueOf(color));

        Button registerButton = findViewById(R.id.btnLogout);
        registerButton.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    private void handleSettings() {
        // Navigate to the settings page
        FloatingActionButton btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            intent.putExtra("selectedColor", selectedColor);
            startActivity(intent);
        });
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
