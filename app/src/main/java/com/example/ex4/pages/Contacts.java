package com.example.ex4.pages;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ex4.ContactAdapter;
import com.example.ex4.ContactViewModel;
import com.example.ex4.MyApplication;
import com.example.ex4.R;
import com.example.ex4.api.ChatAPI;
import com.example.ex4.api.ICallback;
import com.example.ex4.db.Db;
import com.example.ex4.schemas.Contact;
import com.example.ex4.schemas.Message;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

public class Contacts extends AppCompatActivity {

    private Db db;
    private int selectedColor;
    private ListView lstContacts;
    private static final int SETTINGS_REQUEST_CODE = 1;
    private ContactViewModel contactViewModel;

    public void setLstContacts(ListView lstContacts) {
        this.lstContacts = lstContacts;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        setLstContacts((ListView) findViewById(R.id.lstContacts));

        db = new Db(getApplicationContext());
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        // Get the intent that started this activity
        Intent intent = getIntent();

        selectedColor = intent.getIntExtra("selectedColor", 0);

        setSelectedColorAndFrame();
        NavigateToAddContact();
        handleSettings();
        NavigateToLogin();
        getContacts();

        contactViewModel.getContactsLiveData().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                // Update your UI components with the new list of messages
                ListView listView = findViewById(R.id.lstContacts);
                final ContactAdapter adapter = new ContactAdapter(contacts, selectedColor);
                listView.setAdapter(adapter);
            }
        });
    }

    // Navigate to the add contact page
    private void NavigateToAddContact() {
        FloatingActionButton bthAdd = findViewById(R.id.btnAdd);
        bthAdd.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddContact.class);
            intent.putExtra("selectedColor", selectedColor);
            startActivityForResult(intent, SETTINGS_REQUEST_CODE);
        });
    }

    private void NavigateToLogin() {
        FloatingActionButton btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(view -> {
            db.deleteAll();
            Intent intent = new Intent(this, Login.class);
            intent.putExtra("selectedColor", selectedColor);
            startActivity(intent);
        });
    }

    private void handleSettings() {
        // Navigate to the settings page
        FloatingActionButton btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            intent.putExtra("selectedColor", selectedColor);
            startActivityForResult(intent, SETTINGS_REQUEST_CODE); // Start the activity for result
        });
    }

    private void getContacts() {
        ChatAPI chatAPI = new ChatAPI();
        chatAPI.getChats(MyApplication.getToken(), status -> {
            if (status) {
                List<Contact> contactList = chatAPI.getContactList();

                new Thread(() -> {
                    // Update the contacts in the database
                    db.setContactsDb(contactList);
                }).start();

                // Update the ViewModel with the new contacts
                contactViewModel.setContacts(contactList);
            }
        });
    }

    private void setButtonAndTextColors(int colorResId) {
        int color = getResources().getColor(colorResId);

        FloatingActionButton addButton = findViewById(R.id.btnAdd);
        addButton.setBackgroundTintList(null);  // Clear the previous background tint
        addButton.setBackgroundTintList(ColorStateList.valueOf(color));

        FloatingActionButton logoutButton = findViewById(R.id.btnLogout);
        logoutButton.setBackgroundTintList(null);  // Clear the previous background tint
        logoutButton.setBackgroundTintList(ColorStateList.valueOf(color));

        FloatingActionButton settingsButton = findViewById(R.id.btnSettings);
        settingsButton.setBackgroundTintList(null);  // Clear the previous background tint
        settingsButton.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTINGS_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            if (data != null) {
                selectedColor = data.getIntExtra("selectedColor", 0);
                setSelectedColorAndFrame();
            }
        }
        List<Contact> contacts = db.getContactsDb();
        contactViewModel.setContacts(contacts);
    }

    private void setImageFrameBackground(int drawableId) {
        ImageView imageFrame = findViewById(R.id.contact_profile_img);
        Drawable drawable = getResources().getDrawable(drawableId);
        imageFrame.setImageDrawable(drawable);
    }

    private void setSelectedColorAndFrame() {
        if (selectedColor != 0) {
            int defaultColor = getResources().getColor(R.color.default_background);
            int purpleColor = getResources().getColor(R.color.purple_background);
            int blueColor = getResources().getColor(R.color.blue_background);

            if (selectedColor == blueColor) {
                setImageFrameBackground(R.drawable.image_blue);
                setButtonAndTextColors(R.color.blue);
            } else if (selectedColor == defaultColor) {
                setImageFrameBackground(R.drawable.image_pink);
                setButtonAndTextColors(R.color.default_color);
            } else if (selectedColor == purpleColor) {
                setImageFrameBackground(R.drawable.image_purple);
                setButtonAndTextColors(R.color.purple);
            }
        } else {
            setButtonAndTextColors(R.color.default_color);
        }
    }
}
