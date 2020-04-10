package com.notemd.main;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class NoteListGestureHandler extends ItemTouchHelper.SimpleCallback {

    private final NotesListAdapter notesListAdapter;

    public NoteListGestureHandler(NotesListAdapter notesListAdapter) {
        super(0, ItemTouchHelper.LEFT);
        this.notesListAdapter = notesListAdapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        this.notesListAdapter.deleteItem(position);
    }
}
