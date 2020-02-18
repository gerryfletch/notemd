package com.notemd;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.notemd.main.MainActivity;
import com.notemd.storage.NoteService;

import java.util.Date;
import java.util.concurrent.Executors;

import io.noties.markwon.Markwon;
import io.noties.markwon.editor.MarkwonEditor;
import io.noties.markwon.editor.MarkwonEditorTextWatcher;
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
        this.note = note;
        EditText editText = findViewById(R.id.editText);
        editText.setText(note.getNote());

        final MarkwonEditor editor = MarkwonEditor.builder(Markwon.create(this))
                .useEditHandler(new NoteEditor())
                .build();

        editor.preRender(editText.getText(), result -> result.dispatchTo(editText.getText()));
        editText.addTextChangedListener(MarkwonEditorTextWatcher.withPreRender(
                editor,
                Executors.newCachedThreadPool(),
                editText));
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
        findViewById(R.id.editButton).setOnClickListener(l -> navigateToView());
        findViewById(R.id.viewButton).setOnClickListener(l -> navigateToView());
    }

    private void navigateToView() {
        Disposable disposable = this.updateNote().observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    composite.clear();
                    Intent goToView = new Intent(WriteActivity.this, ViewActivity.class);
                    goToView.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    goToView.putExtra("note", note);
                    startActivity(goToView);
                });
        composite.add(disposable);
    }

    private Completable updateNote() {
        if (isNoteDisplayed) {
            String noteContent = ((EditText) findViewById(R.id.editText)).getText().toString();
            if (!noteContent.equals(note.getNote())) {
                Note updatedNote = Note.update(note, noteContent);
                this.note = updatedNote;
                return this.noteService.updateNote(updatedNote);
            }
        }
        return Completable.complete();
    }
}
