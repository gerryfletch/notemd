package com.notemd;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.notemd.storage.DateConverter;

import java.util.Date;

@Entity
public class Note implements Comparable<Note> {
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

    public Note(long noteId, String title, Date created, Date modified, String note) {
        this.noteId = noteId;
        this.title = title;
        this.created = created;
        this.modified = modified;
        this.note = note;
    }

    static Note update(Note prev, String note) {
        String[] lines = note.split("\n");
        String title = lines.length > 0 ? lines[0] : "";
        return new Note(
                prev.getNoteId(),
                title,
                prev.getCreated(),
                new Date(),
                note
        );
    }

    public long getNoteId() {
        return noteId;
    }

    public String getTitle() {
        return title;
    }

    public Date getCreated() {
        return created;
    }

    public Date getModified() {
        return modified;
    }

    public String getNote() {
        return note;
    }

    @Override
    public int compareTo(Note o) {
        return o.getModified().compareTo(getModified());
    }
}
