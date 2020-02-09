package com.notemd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        this.renderNoteList();
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
