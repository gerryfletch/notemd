package com.notemd.storage;

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

    static class NoteDescription extends RecyclerView.ViewHolder {
        TextView title;
        TextView createdAt;
        TextView modifiedAt;

        NoteDescription(View v) {
            super(v);
            title = v.findViewById(R.id.rowTitle);
            createdAt = v.findViewById(R.id.createdAt);
            modifiedAt = v.findViewById(R.id.modifiedAt);
        }
    }

    NotesListAdapter(List<NoteMeta> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public NoteDescription onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_note_item, parent, false);
        return new NoteDescription(v);
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
