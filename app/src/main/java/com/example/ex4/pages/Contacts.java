package com.example.ex4.pages;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.ex4.ContactAdapter;
import com.example.ex4.MyApplication;
import com.example.ex4.R;
import com.example.ex4.api.ChatAPI;
import com.example.ex4.api.ICallback;
import com.example.ex4.db.AppDB;
import com.example.ex4.db.AppDB2;
import com.example.ex4.db.ContactDao;
import com.example.ex4.db.ConvertersContacts;
import com.example.ex4.schemas.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Contacts extends AppCompatActivity {

    private AppDB2 db;
    ContactDao contactDao;
    private int selectedColor;
    private ListView lstContacts;
    private static final int SETTINGS_REQUEST_CODE = 1;

    public void setLstContacts(ListView lstContacts) {
        this.lstContacts = lstContacts;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        setLstContacts((ListView) findViewById(R.id.lstContacts));

        // Get the intent that started this activity
        Intent intent = getIntent();

        selectedColor = intent.getIntExtra("selectedColor", 0);

        setSelectedColorAndFrame();
        NavigateToAddContact();
        handleSettings();
        NavigateToLogin();
        getContacts();
    }

    // Navigate to the add contact page
    private void NavigateToAddContact() {
        FloatingActionButton bthAdd = findViewById(R.id.btnAdd);
        bthAdd.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddContact.class);
            intent.putExtra("selectedColor", selectedColor);
            startActivity(intent);
        });
    }

    private void NavigateToLogin() {
        FloatingActionButton btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(view -> {
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

    private void getChatsDb (List<Contact> chatList) {
        db = Room.databaseBuilder(getApplicationContext(),
                        AppDB2.class, "Foo")
                .allowMainThreadQueries()
                .build();
        contactDao = db.contactDao();

        contactDao.deleteAll();
        contactDao.insert(chatList.toArray(new Contact[0]));
    }

    private void getContacts() {

        ChatAPI chatAPI = new ChatAPI();
        chatAPI.getChats(MyApplication.getToken(), new ICallback() {
            @Override
            public void status(boolean status) {
                if (status) {
                    List<Contact> contactList = chatAPI.getContactList();
                    getChatsDb(contactList);
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
    }

    private void setSelectedColorAndFrame() {
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
        } else {
            setButtonAndTextColors(R.color.default_color);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Call getContacts() method again when the activity resumes
        getContacts();
    }

}
