package com.example.todolist.Data;

import android.app.Application;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = {NoteEntity.class}, version = 1, exportSchema = false)
@TypeConverters(value = {NotesTypeConverter.class})
public abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase instance;
    public abstract DatabaseDao dao();

    public static NotesDatabase getInstance(Application app){
        if(instance == null){
            instance = Room.databaseBuilder(
                    app.getApplicationContext(),
                    NotesDatabase.class,
                    "NotesDatabase"
            ).allowMainThreadQueries().build();
        }
        return instance;
    }


}
