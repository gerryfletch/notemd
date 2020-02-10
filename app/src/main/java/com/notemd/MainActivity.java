package com.notemd;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.notemd.storage.AppDatabase;
import com.notemd.storage.Note;
import com.notemd.storage.NoteDao;
import com.notemd.storage.NoteMeta;
import com.notemd.storage.NotesListAdapter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        this.renderNoteList();
        this.testDao();
    }

    @SuppressLint("CheckResult")
    private void testDao() {
        Note note = new Note(0, "Database Note", new Date(), new Date(), "blabla");
        System.out.println("Created note.");

        NoteDao noteDao = AppDatabase.get(getApplicationContext()).noteDao();

        System.out.println("Got DB instance reference.");

        noteDao.insertNote(note)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        noteId -> System.out.println("Successfully stored note with id: " + noteId),
                        e -> System.out.println("ERROR:" + e.getMessage())
                );
    }

    private void renderNoteList() {
        RecyclerView recyclerView = findViewById(R.id.notesList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<NoteMeta> notes = Arrays.asList(
                new NoteMeta("Intro to Python, Class One", "TWO DAYS AGO", "YESTERDAY"),
                new NoteMeta("Scala Futures API", "THREE DAYS AGO", "THREE DAYS AGO"),
                new NoteMeta("My First Note", "01/11/2019", "03/11/2019"),
                new NoteMeta("Intro to Python, Class One", "TWO DAYS AGO", "YESTERDAY"),
                new NoteMeta("Scala Futures API", "THREE DAYS AGO", "THREE DAYS AGO"),
                new NoteMeta("My First Note", "01/11/2019", "03/11/2019"),
                new NoteMeta("Intro to Python, Class One", "TWO DAYS AGO", "YESTERDAY"),
                new NoteMeta("Scala Futures API", "THREE DAYS AGO", "THREE DAYS AGO"),
                new NoteMeta("My First Note", "01/11/2019", "03/11/2019"),
                new NoteMeta("Intro to Python, Class One", "TWO DAYS AGO", "YESTERDAY"),
                new NoteMeta("Scala Futures API", "THREE DAYS AGO", "THREE DAYS AGO"),
                new NoteMeta("My First Note", "01/11/2019", "03/11/2019"),
                new NoteMeta("Intro to Python, Class One", "TWO DAYS AGO", "YESTERDAY"),
                new NoteMeta("Scala Futures API", "THREE DAYS AGO", "THREE DAYS AGO"),
                new NoteMeta("My First Note", "01/11/2019", "03/11/2019"),
                new NoteMeta("Intro to Python, Class One", "TWO DAYS AGO", "YESTERDAY"),
                new NoteMeta("Scala Futures API", "THREE DAYS AGO", "THREE DAYS AGO"),
                new NoteMeta("My First Note", "01/11/2019", "03/11/2019"),
                new NoteMeta("Intro to Python, Class One", "TWO DAYS AGO", "YESTERDAY"),
                new NoteMeta("Scala Futures API", "THREE DAYS AGO", "THREE DAYS AGO"),
                new NoteMeta("My First Note", "01/11/2019", "03/11/2019")
        );

        recyclerView.setAdapter(new NotesListAdapter(notes));
    }
}
