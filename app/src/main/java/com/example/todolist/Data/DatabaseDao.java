package com.example.todolist.Data;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.List;

@TypeConverters(value = {NotesTypeConverter.class})
@Dao
public interface DatabaseDao {
    @Query("select * from notes")
    List<NoteEntity> getAllNotes();

    @Query("select * from notes where name like '%' || :keyword || '%'")
    List<NoteEntity> searchNotes(String keyword);

    @Query("select * from notes where id = :id")
    NoteEntity getNoteWithId(int id);

    @Update
    void updateNote(NoteEntity note);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(NoteEntity note);

    @Delete
    void deleteNote(NoteEntity note);

}
