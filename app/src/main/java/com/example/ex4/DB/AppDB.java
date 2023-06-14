package com.example.ex4.DB;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.ex4.schemas.Chat;

@Database(entities = {Chat.class}, version = 1)
 public abstract class AppDB extends RoomDatabase {
 public abstract ChatDao chatDao();
}

