package com.notemd.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.notemd.R;

import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteDescription> {

    private final List<NoteMeta> data;
    private final OnNoteDescriptionClick clickHandler;

    public interface OnNoteDescriptionClick {
        void onNoteDescriptionClick(int position);
    }

    static class NoteDescription extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView createdAt;
        TextView modifiedAt;
        OnNoteDescriptionClick clickHandler;

        NoteDescription(View v, OnNoteDescriptionClick clickHandler) {
            super(v);
            this.title = v.findViewById(R.id.rowTitle);
            this.createdAt = v.findViewById(R.id.createdAt);
            this.modifiedAt = v.findViewById(R.id.modifiedAt);
            this.clickHandler = clickHandler;

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.clickHandler.onNoteDescriptionClick(getAdapterPosition());
        }
    }

    public NotesListAdapter(List<NoteMeta> data, OnNoteDescriptionClick clickHandler) {
        this.data = data;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public NoteDescription onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_note_item, parent, false);
        return new NoteDescription(v, clickHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteDescription holder, int position) {
        NoteMeta note = data.get(position);
        holder.title.setText(note.getTitle());
        holder.createdAt.setText(note.getCreatedAt());
        holder.modifiedAt.setText(note.getModifiedAt());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
