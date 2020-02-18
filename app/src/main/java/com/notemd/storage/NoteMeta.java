package com.notemd.storage;

public class NoteMeta {
    private final String title;
    private final String createdAt;
    private final String modifiedAt;

    public NoteMeta(String title, String createdAt, String modifiedAt) {
        this.title = title;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static NoteMeta fromNote(Note note) {
        return new NoteMeta(
                note.getTitle(),
                note.getCreated().toString(),
                note.getModified().toString()
        );
    }

    public String getTitle() {
        return title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }
}
