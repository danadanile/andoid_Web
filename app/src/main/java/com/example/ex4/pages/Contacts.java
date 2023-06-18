package com.example.ex4.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import com.example.ex4.ContactAdapter;
import com.example.ex4.R;
import com.example.ex4.api.ChatAPI;
import com.example.ex4.api.ICallback;
import com.example.ex4.schemas.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Contacts extends AppCompatActivity {
    private String token;
    private ListView lstContacts;

    public void setLstContacts(ListView lstContacts) {
        this.lstContacts = lstContacts;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        setLstContacts(findViewById(R.id.lstContacts));

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Check if the intent has extras
        if (intent != null && intent.getExtras() != null) {
            // Retrieve the value of "token" from the intent extras
            setToken(intent.getExtras().getString("token"));
        }
        NavigateToAddContact();
        NavigateToLogin();
        getContacts();
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Navigate to the add contact page
    private void NavigateToAddContact() {
        FloatingActionButton bthAdd = findViewById(R.id.btnAdd);
        bthAdd.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddContact.class);
            intent.putExtra("token", token);
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

            chatAPI.getChats(token, new ICallback() {
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
}
