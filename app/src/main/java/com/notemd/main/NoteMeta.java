package com.notemd.main;

import com.notemd.Note;

import org.ocpsoft.prettytime.PrettyTime;

public class NoteMeta {
    private final long noteId;
    private final String title;
    private final String createdAt;
    private final String modifiedAt;

    private NoteMeta(long noteId, String title, String createdAt, String modifiedAt) {
        this.noteId = noteId;
        this.title = title;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static NoteMeta fromNote(Note note) {
        PrettyTime prettyTime = new PrettyTime();
        return new NoteMeta(
                note.getNoteId(),
                note.getTitle(),
                prettyTime.format(note.getCreated()),
                prettyTime.format(note.getModified())
        );
    }

    public long getNoteId() {
        return noteId;
    }

    String getTitle() {
        return title;
    }

    String getCreatedAt() {
        return createdAt;
    }

    String getModifiedAt() {
        return modifiedAt;
    }

}
