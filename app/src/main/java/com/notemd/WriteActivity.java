package com.notemd;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.notemd.storage.NoteService;

import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class WriteActivity extends AppCompatActivity {

    private CompositeDisposable composite;
    private NoteService noteService;
    private Intent returnToMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        composite = new CompositeDisposable();
        noteService = new NoteService(getApplicationContext());
        returnToMain = new Intent(WriteActivity.this, MainActivity.class);

        this.setupBackButton();

        long noteId = getIntent().getLongExtra("noteId", 0);
        if (noteId <= 0) {
            createEmptyNote();
        } else {
            getNote(noteId);
        }
    }

    private void createEmptyNote() {
        Note blankNote = new Note(0, "", new Date(), new Date(), "");
        Disposable disposable = noteService.storeNote(blankNote)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(this::getNote)
                .doOnError(e -> startActivity(returnToMain))
                .subscribe();
        composite.add(disposable);
    }

    private void getNote(long noteId) {
        Disposable disposable = noteService.getNote(noteId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(this::displayNote)
                .doOnError(e -> startActivity(returnToMain))
                .subscribe();
        composite.add(disposable);
    }

    private void displayNote(Note note) {
        composite.clear();
        ((EditText) findViewById(R.id.editText)).setText(note.getNote());
    }

    private void setupBackButton() {
        findViewById(R.id.backButton).setOnClickListener(l -> startActivity(returnToMain));
    }
}
