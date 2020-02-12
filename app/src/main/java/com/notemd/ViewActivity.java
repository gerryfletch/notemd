package com.notemd;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.notemd.main.MainActivity;

import io.noties.markwon.Markwon;

public class ViewActivity extends AppCompatActivity {

    private Intent returnToMain;
    private Markwon markwon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        returnToMain = new Intent(ViewActivity.this, MainActivity.class);
        markwon = Markwon.create(getApplicationContext());

        Note note = (Note) getIntent().getSerializableExtra("note");
        if (note == null || note.getNoteId() <= 0) {
            startActivity(returnToMain);
        } else {
            displayMarkdown(note);
        }
    }

    private void displayMarkdown(Note note) {
        TextView displayText = findViewById(R.id.viewText);
        System.out.println(displayText);
        markwon.setMarkdown(displayText, note.getNote());
    }
}
