package com.notemd;

public class NoteMeta {
    private final String title;
    private final String createdAt;
    private final String modifiedAt;

    public NoteMeta(String title, String createdAt, String modifiedAt) {
        this.title = title;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
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
