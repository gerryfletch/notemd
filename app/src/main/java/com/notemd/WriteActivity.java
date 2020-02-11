package com.notemd;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.notemd.storage.NoteService;

import java.util.Date;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class WriteActivity extends AppCompatActivity {

    private CompositeDisposable composite;
    private NoteService noteService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        composite = new CompositeDisposable();
        noteService = new NoteService(getApplicationContext());
        long noteId = getIntent().getLongExtra("noteId", 0);
        System.out.println("Note ID from intent: " + noteId);
    }

//    private void createEmptyNote() {
//        Note blankNote = new Note(0, "", new Date(), new Date(), "");
//        Disposable disposable = noteService.storeNote(blankNote).subscribe();
//        composite.add(disposable);
//    }
}
