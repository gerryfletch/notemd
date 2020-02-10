package com.notemd;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity
public class Note {
    @PrimaryKey(autoGenerate = true)
    private long noteId;

    @ColumnInfo
    private String title;

    @ColumnInfo
    @TypeConverters(DateConverter.class)
    private Date created;

    @ColumnInfo
    @TypeConverters(DateConverter.class)
    private Date modified;

    @ColumnInfo
    private String note;

    Note(long noteId, String title, Date created, Date modified, String note) {
        this.noteId = noteId;
        this.title = title;
        this.created = created;
        this.modified = modified;
        this.note = note;
    }

    long getNoteId() {
        return noteId;
    }

    String getTitle() {
        return title;
    }

    Date getCreated() {
        return created;
    }

    Date getModified() {
        return modified;
    }

    String getNote() {
        return note;
    }
}
