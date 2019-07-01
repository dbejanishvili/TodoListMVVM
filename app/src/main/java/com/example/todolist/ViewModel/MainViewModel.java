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

import java.util.List;


public class MainViewModel extends AndroidViewModel {
    private MutableLiveData<List<NoteEntity>> notes;
    private DatabaseDao dao;

    public MainViewModel(@NonNull Application application) {
        super(application);
        dao = NotesDatabase.getInstance(application).dao();
        notes = new MutableLiveData<>();

    }

    public void searchNotes(String keyword){
        if(keyword.equals(""))
            new getAllNotesAsyncTask(dao,notes).execute();
        else
            new searchNoteAsyncTask(dao, notes).execute(keyword);
    }

    public LiveData<List<NoteEntity>> getNotes(){
        return notes;
    }

    private static class getAllNotesAsyncTask extends AsyncTask<Void, Void, List<NoteEntity>> {
        private DatabaseDao dao;
        private MutableLiveData<List<NoteEntity>> notes;

        getAllNotesAsyncTask(DatabaseDao dao, MutableLiveData<List<NoteEntity>> notes){
            this.dao = dao;
            this.notes = notes;
        }

        @Override
        protected List<NoteEntity> doInBackground(Void... voids) {
            return dao.getAllNotes();
        }

        @Override
        protected void onPostExecute(List<NoteEntity> noteEntities) {
            super.onPostExecute(noteEntities);
            notes.setValue(noteEntities);
        }
    }
    private static class searchNoteAsyncTask extends AsyncTask<String, Void, List<NoteEntity>>{
        private DatabaseDao dao;
        private MutableLiveData<List<NoteEntity>> notes;

        searchNoteAsyncTask(DatabaseDao dao, MutableLiveData<List<NoteEntity>> notes){
            this.dao=dao;
            this.notes = notes;
        }


        @Override
        protected List<NoteEntity> doInBackground(String... strings) {
            return dao.searchNotes(strings[0]);
        }

        @Override
        protected void onPostExecute(List<NoteEntity> noteEntities) {
            super.onPostExecute(noteEntities);
            notes.setValue(noteEntities);
        }
    }


}
