package com.notemd;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

    static AppDatabase get(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "notes").build();
    }
}