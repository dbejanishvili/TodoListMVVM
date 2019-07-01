package com.example.todolist.View;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.todolist.Constants;
import com.example.todolist.Data.NoteEntity;
import com.example.todolist.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteHolder> {
    private List<NoteEntity> notes;
    private OnNoteClickListener noteListener;

    public NotesAdapter(){
        notes = new ArrayList<>();
        noteListener = new OnNoteClickListener() {
            @Override
            public void onNoteClick(NoteEntity note, Context context) {
                Intent intent = new Intent(context, EditNoteActivity.class);
                intent.putExtra(Constants.NOTE_EXTRA, note);
                intent.putExtra(Constants.NEWLY_CREATED_EXTRA,false);
                context.startActivity(intent);
            }
        };

    }
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_list_item, viewGroup, false);
        return new NoteHolder(view, noteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int i) {
        NoteEntity note = notes.get(i);
        noteHolder.noteName.setText(note.getName());
        noteHolder.noteContent.setText(note.getContent());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void updateData(List<NoteEntity> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }


    public class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView noteName;
        TextView noteContent;
        OnNoteClickListener noteListener;

        public NoteHolder(@NonNull View itemView, OnNoteClickListener noteListener) {
            super(itemView);
            this.noteContent = itemView.findViewById(R.id.noteItemContent);
            this.noteName = itemView.findViewById(R.id.noteItemName);
            this.noteListener = noteListener;
            itemView.setOnClickListener(this);
        }

        public void onClick(View view){
            noteListener.onNoteClick(notes.get(getAdapterPosition()), view.getContext());
        }
    }

    public interface OnNoteClickListener{
        void onNoteClick(NoteEntity note, Context context);
    }


}
