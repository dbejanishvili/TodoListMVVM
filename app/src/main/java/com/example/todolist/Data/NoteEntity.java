package com.example.todolist.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "notes")
@TypeConverters(value =  {NotesTypeConverter.class})
public class NoteEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;


    @ColumnInfo(name = "isPinned")
    private boolean isPinned;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "editDate")
    private Date editDate;

    public NoteEntity(){ this("", "", false); }

    public NoteEntity(String name){
        this(name, "",false);
    }

    public NoteEntity(String name, boolean isPinned){ this(name, "", isPinned); }

    public NoteEntity(String name, String tasks){
        this(name, tasks, false);
    }

    public NoteEntity(String name,String tasks, boolean isPinned){
        this.name = name;
        this.content = tasks;
        this.isPinned = isPinned;
        this.editDate = new Date(System.currentTimeMillis());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.editDate = new Date(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }


    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned){
        this.isPinned = pinned;
    }

    public void changePinned(){
        isPinned = !isPinned;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.editDate = new Date(System.currentTimeMillis());
    }

    public void setEditDate(Date date){
        editDate = date;
    }
    public Date getEditDate() {
        return editDate;
    }

    public void setId(int id) {
        this.id = id;
    }
}
