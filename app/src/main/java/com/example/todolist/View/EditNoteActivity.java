package com.example.todolist.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.todolist.Constants;
import com.example.todolist.Data.NoteEntity;
import com.example.todolist.R;
import com.example.todolist.ViewModel.EditNoteViewModel;


public class EditNoteActivity extends AppCompatActivity {
    private ImageButton backButton;
    private ImageButton pinButton;
    private EditText noteName;
    private EditText noteContent;
    private TextView editDate;
    private EditNoteViewModel model;
    private boolean newlyCreated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        NoteEntity note = (NoteEntity) getIntent().getSerializableExtra(Constants.NOTE_EXTRA);
        newlyCreated = getIntent().getBooleanExtra(Constants.NEWLY_CREATED_EXTRA,true);
        drawNote(note);

        backButton = findViewById(R.id.backButton);
        pinButton = findViewById(R.id.pinButton);
        noteContent = findViewById(R.id.task);
        noteName = findViewById(R.id.NoteName);
        editDate = findViewById(R.id.lastEdit);
        model = ViewModelProviders.of(this).get(EditNoteViewModel.class);
        model.setNote(note);
        model.getPinIconResource().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer res) {
                if(res != null)
                    pinButton.setImageResource(res);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newlyCreated){
                    model.insert(noteName.getText().toString(), noteContent.getText().toString());
                } else{
                    model.update(noteName.getText().toString(), noteContent.getText().toString());
                }
            }
        });

        pinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.changePin();
            }
        });

    }

    private void drawNote(NoteEntity note) {
        if(note.isPinned())
            pinButton.setImageResource(R.drawable.pin_button_filled);
        else
            pinButton.setImageResource(R.drawable.pin_button);
        noteName.setText(note.getName());
        noteContent.setText(note.getContent());

        editDate.setText(note.getEditDate().toString());
    }


}
