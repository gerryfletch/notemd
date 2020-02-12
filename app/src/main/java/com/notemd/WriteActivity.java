package com.notemd;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.notemd.main.MainActivity;
import com.notemd.storage.NoteService;

import java.util.Date;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class WriteActivity extends AppCompatActivity {

    private CompositeDisposable composite;
    private NoteService noteService;
    private Intent returnToMain;

    private boolean isNoteDisplayed;
    private Note note;

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
        this.setupViewButton();

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
        this.isNoteDisplayed = true;
        System.out.println("Displaying note. Note should be set...");
        this.note = note;
        ((EditText) findViewById(R.id.editText)).setText(note.getNote());
    }

    private void setupBackButton() {
        findViewById(R.id.backButton).setOnClickListener(l -> {
            Disposable disposable = this.updateNote()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        composite.clear();
                        startActivity(returnToMain);
                    });
            composite.add(disposable);
        });
    }

    private void setupViewButton() {
        findViewById(R.id.editButton).setOnClickListener(l -> startActivity(createViewIntent()));
        findViewById(R.id.viewButton).setOnClickListener(l -> startActivity(createViewIntent()));
    }

    private Intent createViewIntent() {
        Intent goToView = new Intent(WriteActivity.this, ViewActivity.class);
        goToView.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        goToView.putExtra("note", note);
        return goToView;
    }

    private Completable updateNote() {
        if (isNoteDisplayed) {
            String noteContent = ((EditText) findViewById(R.id.editText)).getText().toString();
            if (!noteContent.equals(note.getNote())) {
                Note updatedNote = Note.update(note, noteContent);
                return this.noteService.updateNote(updatedNote);
            }
        }
        return Completable.complete();
    }
}
