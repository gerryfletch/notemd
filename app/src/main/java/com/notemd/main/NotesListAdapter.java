package com.notemd.main;

import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

    static class NoteDescription extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener {
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

            v.setOnTouchListener(this);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.clickHandler.onNoteDescriptionClick(getAdapterPosition());
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setElevation(5);
            } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                v.setElevation(1);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                v.performClick();
            }
            return true;
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
