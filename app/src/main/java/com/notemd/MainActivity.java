package com.notemd;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.notemd.storage.Note;
import com.notemd.storage.NoteMeta;
import com.notemd.storage.NoteService;
import com.notemd.storage.NotesListAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private CompositeDisposable composite;
    private NoteService noteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.noteService = new NoteService(getApplicationContext());
        this.composite = new CompositeDisposable();

        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        this.prepareNoteListView();
        this.asyncRenderNoteList();
    }

    @SuppressLint("CheckResult")
    private void asyncRenderNoteList() {
        Disposable disposable = this.noteService.getNotes()
                .map(this::notesToMeta)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::renderNoteList,
                        e -> System.out.println("ERROR: " + e.getMessage())
                );
        this.composite.add(disposable);
    }

    private List<NoteMeta> notesToMeta(List<Note> notes) {
        List<NoteMeta> result = new ArrayList<>();
        for (Note note : notes) result.add(NoteMeta.fromNote(note));
        return result;
    }

    private void renderNoteList(List<NoteMeta> noteMetas) {
        this.composite.dispose();
        RecyclerView recyclerView = findViewById(R.id.notesList);
        recyclerView.setAdapter(new NotesListAdapter(noteMetas));

        ((TextView) findViewById(R.id.totalValue)).setText(String.valueOf(noteMetas.size()));
    }

    private void prepareNoteListView() {
        RecyclerView recyclerView = findViewById(R.id.notesList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new NotesListAdapter(new ArrayList<>()));
    }
}
