package com.example.ex4.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.ex4.schemas.Contact;

@Database(entities = {Contact.class}, version = 1)
@TypeConverters({ConvertersContacts.class}) // Add the ConvertersContacts class here
public abstract class AppDB extends RoomDatabase {
    public abstract ContactDao contactDao();
}

