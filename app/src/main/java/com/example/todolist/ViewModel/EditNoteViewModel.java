package com.example.todolist.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.todolist.Data.DatabaseDao;
import com.example.todolist.Data.NoteEntity;
import com.example.todolist.Data.NotesDatabase;
import com.example.todolist.R;

public class EditNoteViewModel extends AndroidViewModel {
    private DatabaseDao dao;
    private NoteEntity note;
    private MutableLiveData<Integer> pinIconRes;


    public EditNoteViewModel(@NonNull Application application) {
        super(application);

        dao = NotesDatabase.getInstance(application).dao();

    }

    public void setNote(NoteEntity note){
        this.note = note;
        setPinIconRes();
    }


    public void insert(String name, String content) {
        note.setContent(content);
        note.setName(name);
        new InsertAsyncTask(dao).execute(note);
    }

    public void update(String name, String content) {
        note.setContent(content);
        note.setName(name);
        new UpdateAsyncTask(dao).execute(note);
    }

    public LiveData<Integer> getPinIconResource(){
        return pinIconRes;
    }

    public void changePin() {
        note.changePinned();
        setPinIconRes();
    }
    private void setPinIconRes(){
        if(note.isPinned()){
            pinIconRes.setValue(R.drawable.pin_button_filled);
        }else{
            pinIconRes.setValue(R.drawable.pin_button);
        }
    }

    private static class InsertAsyncTask extends AsyncTask<NoteEntity,Void, Void>{

        private DatabaseDao dao;
        InsertAsyncTask(DatabaseDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            dao.insertNote(noteEntities[0]);
            return  null;
        }
    }
    private static class UpdateAsyncTask extends AsyncTask<NoteEntity,Void, Void>{

        private DatabaseDao dao;
        UpdateAsyncTask(DatabaseDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            dao.updateNote(noteEntities[0]);
            return  null;
        }
    }
}
