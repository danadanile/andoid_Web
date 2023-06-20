package com.example.ex4.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.ex4.schemas.Chat;

@Database(entities = {Chat.class}, version = 1)
@TypeConverters({ConvertersChat.class}) // Add the ConvertersContacts class here
public abstract class AppDBCh extends RoomDatabase {
    public abstract ChatDao chatDao();
}