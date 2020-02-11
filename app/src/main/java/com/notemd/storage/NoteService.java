package com.notemd.storage;

import android.content.Context;

import com.notemd.Note;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class NoteService {

    private final NoteDao noteDao;

    public NoteService(Context context) {
        this.noteDao = AppDatabase.get(context).noteDao();
    }

    public Single<Long> storeNote(Note note) {
        return this.noteDao.insertNote(note).subscribeOn(Schedulers.io());
    }

    public Single<List<Note>> getNotes() {
        return this.noteDao.getAllNotes().subscribeOn(Schedulers.io());
    }

}
