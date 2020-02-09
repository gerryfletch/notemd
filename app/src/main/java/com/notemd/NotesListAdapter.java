package com.notemd;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteDescription> {

    private final List<NoteMeta> data;

    static class NoteDescription extends RecyclerView.ViewHolder {
        TextView textView;

        NoteDescription(TextView v) {
            super(v);
            textView = v;
        }
    }

    NotesListAdapter(List<NoteMeta> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public NoteDescription onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteDescription(new TextView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteDescription holder, int position) {
        holder.textView.setText(data.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
