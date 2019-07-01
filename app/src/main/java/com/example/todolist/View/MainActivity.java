package com.example.todolist.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.todolist.Constants;
import com.example.todolist.Data.NoteEntity;
import com.example.todolist.R;
import com.example.todolist.ViewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NotesAdapter pinnedNoteAdapter;
    private NotesAdapter unpinnedNoteAdapter;
    private RecyclerView pinnedList;
    private RecyclerView unpinnedList;
    private EditText  searchField;
    private Button newNoteButton;
    private MainViewModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pinnedList = findViewById(R.id.pinnedNotes);
        unpinnedList = findViewById(R.id.unpinnedNotes);
        searchField = findViewById(R.id.search_field);
        newNoteButton = findViewById(R.id.newNoteButton);

        pinnedNoteAdapter = new NotesAdapter();
        unpinnedNoteAdapter = new NotesAdapter();

        pinnedList.setLayoutManager(new GridLayoutManager(this,2));
        unpinnedList.setLayoutManager(new GridLayoutManager(this,2));
        pinnedList.setAdapter(pinnedNoteAdapter);
        unpinnedList.setAdapter(unpinnedNoteAdapter);


        model = ViewModelProviders.of(this).get(MainViewModel.class);
        model.getNotes().observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(@Nullable List<NoteEntity> noteEntities) {
                ArrayList<NoteEntity> unpinned = new ArrayList<>();
                ArrayList<NoteEntity> pinned = new ArrayList<>();
                if(noteEntities == null) return;

                for (NoteEntity note : noteEntities) {
                    if (note.isPinned()) pinned.add(note);
                    else unpinned.add(note);
                }
                pinnedNoteAdapter.updateData(pinned);
                unpinnedNoteAdapter.updateData(unpinned);
            }
        });
        searchField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    model.searchNotes(searchField.getText().toString());
                }
                return false;
            }
        });

        newNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EditNoteActivity.class);
                intent.putExtra(Constants.NOTE_EXTRA,new NoteEntity());
                intent.putExtra(Constants.NEWLY_CREATED_EXTRA,true);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        searchField.setText("");
        model.searchNotes("");
    }
}
