package com.example.ex4.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.ex4.schemas.Chat;
import com.example.ex4.schemas.Contact;

    @Database(entities = {Contact.class, Chat.class}, version = 1)
    @TypeConverters({Converters.class}) // Add the ConvertersContacts class here
    public abstract class AppDB2 extends RoomDatabase {
        public abstract ContactDao contactDao();
        public abstract ChatDao chatDao();
    }

