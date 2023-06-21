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

    public void setMessageDb(Message msg, int chatId) {
        Chat chat = chatDao.get(chatId);

        // Check if the chat exists in the table
        if (chat != null) {
            // Create a new array with increased size
            Message[] newMessages = new Message[chat.getMessages().length + 1];

            // Copy existing messages to the new array
            System.arraycopy(chat.getMessages(), 0, newMessages, 0, chat.getMessages().length);

            // Add the new message to the last index of the new array
            newMessages[newMessages.length - 1] = msg;

            // Update the chat object with the new messages array
            chat.setMessages(newMessages);

            chatDao.update(chat);
        }
    }

    public void setMessagesDb(Message[] messages, int chatId) {
        Chat chat = chatDao.get(chatId);

        // Check if the chat exists in the table
        if (chat != null) {
            chat.setMessages(messages);
            chatDao.update(chat);
        }
    }

    public void addContactDb(Contact newContact) {
        contactDao.insert(newContact);
    }
}