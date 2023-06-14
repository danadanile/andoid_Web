package com.example.ex4.DB;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.ex4.schemas.Chat;


@TypeConverters(Converters.class)
@Database(entities = {Chat.class}, version = 1)
 public abstract class AppDB extends RoomDatabase {
 public abstract ChatDao chatDao();
}

