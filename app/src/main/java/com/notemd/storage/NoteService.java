package com.notemd.storage;

import android.content.Context;

import com.notemd.Note;

import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
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

    public Completable updateNote(Note note) {
        return this.noteDao.updateNote(note).subscribeOn(Schedulers.io());
    }

    public Single<List<Note>> getNotes() {
        return this.noteDao.getAllNotes()
                .map(list -> {
                    Collections.sort(list);
                    return list;
                })
                .subscribeOn(Schedulers.io());
    }

    public Maybe<Note> getNote(long noteId) {
        return this.noteDao.getNote(noteId).subscribeOn(Schedulers.io());
    }

}
