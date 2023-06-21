package com.example.ex4.db;

import android.content.Context;

import androidx.room.Room;

import com.example.ex4.schemas.Chat;
import com.example.ex4.schemas.Contact;
import com.example.ex4.schemas.Message;

import java.util.List;

public class Db {
    private Context context;
    private AppDB2 db;
    ContactDao contactDao;
    ChatDao chatDao;

    public Db(Context context) {
        this.context = context;
        db = Room.databaseBuilder(context.getApplicationContext(),
                        AppDB2.class, "Db")
                .allowMainThreadQueries()
                .build();
        contactDao = db.contactDao();
        chatDao = db.chatDao();
    }

    public void setContactsDb(List<Contact> chatList) {
        contactDao.deleteAll(); ////////////////////////
        contactDao.insert(chatList.toArray(new Contact[0]));
    }

    public void setChatDb(Chat chat) {
        if (chatDao.get(chat.getId()) == null) {
            chatDao.insert(chat);
        } else {
            chatDao.update(chat);
        }
    }

    public void setMessagesDb(Message msg, int id) {
//        if (chatDao.get(id) == null) {
//            chatDao.insert(chat);
//        } else {
//            chatDao.update(chat);
//        }
    }

    public void addContactDb(Contact newContact) {
        contactDao.insert(newContact);
    }
}