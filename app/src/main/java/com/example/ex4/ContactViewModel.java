package com.example.ex4;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ex4.schemas.Contact;

import java.util.List;

public class ContactViewModel extends ViewModel {
    private MutableLiveData<List<Contact>> contactsLiveData;

    public LiveData<List<Contact>> getContactsLiveData() {
        if (contactsLiveData == null) {
            contactsLiveData = new MutableLiveData<>();
        }
        return contactsLiveData;
    }

    public void setContacts(List<Contact> Contacts) {
        if (contactsLiveData != null) {
            contactsLiveData.setValue(Contacts);
        }
    }
}

