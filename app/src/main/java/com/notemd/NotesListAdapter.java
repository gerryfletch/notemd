package com.notemd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteDescription> {

    private final List<NoteMeta> data;

    static class NoteDescription extends RecyclerView.ViewHolder {
        TextView title;
        TextView createdAt;

        NoteDescription(View v) {
            super(v);
            title = v.findViewById(R.id.rowTitle);
            createdAt = v.findViewById(R.id.createdAt);
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
        holder.title.setText(data.get(position).getTitle());
        holder.createdAt.setText(data.get(position).getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
