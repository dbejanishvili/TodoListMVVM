package com.example.todolist.Data;


import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class NotesTypeConverter {

    @TypeConverter
    public static Long fromDate(Date date){
        if (date == null)
            return null;
        return date.getTime();
    }

    @TypeConverter
    public static Date toDate(Long milliseconds){
        return milliseconds == null ? null : new Date(milliseconds);
    }
}
