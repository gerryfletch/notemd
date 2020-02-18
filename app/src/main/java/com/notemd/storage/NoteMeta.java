package com.notemd.storage;

import org.ocpsoft.prettytime.PrettyTime;

public class NoteMeta {
    private final String title;
    private final String createdAt;
    private final String modifiedAt;

    private NoteMeta(String title, String createdAt, String modifiedAt) {
        this.title = title;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static NoteMeta fromNote(Note note) {
        PrettyTime prettyTime = new PrettyTime();
        return new NoteMeta(
                note.getTitle(),
                prettyTime.format(note.getCreated()),
                prettyTime.format(note.getModified())
        );
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
