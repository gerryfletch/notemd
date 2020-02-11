package com.notemd.storage;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.notemd.Note;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    Single<List<Note>> getAllNotes();

    @Query("SELECT * FROM note WHERE noteId = :noteId LIMIT 1")
    Maybe<Note> getNote(long noteId);

    @Insert
    Single<Long> insertNote(Note note);

}
