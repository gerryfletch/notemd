package com.notemd;

import android.graphics.Typeface;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.StyleSpan;

import androidx.annotation.NonNull;

import io.noties.markwon.core.spans.StrongEmphasisSpan;
import io.noties.markwon.editor.AbstractEditHandler;
import io.noties.markwon.editor.MarkwonEditorUtils;
import io.noties.markwon.editor.PersistedSpans;

class NoteEditor extends AbstractEditHandler<StrongEmphasisSpan> {

    @Override
    public void configurePersistedSpans(@NonNull PersistedSpans.Builder builder) {
        builder.persistSpan(Bold.class, Bold::new);
    }

    @Override
    public void handleMarkdownSpan(@NonNull PersistedSpans persistedSpans, @NonNull Editable editable, @NonNull String input, @NonNull StrongEmphasisSpan span, int spanStart, int spanTextLength) {
        final MarkwonEditorUtils.Match match = MarkwonEditorUtils.findDelimited(input, spanStart, "**", "__");
        if (match != null) {
            editable.setSpan(
                    persistedSpans.get(Bold.class),
                    match.start(),
                    match.end(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }
    }

    @NonNull
    @Override
    public Class<StrongEmphasisSpan> markdownSpanType() {
        return StrongEmphasisSpan.class;
    }

    class Bold extends StyleSpan {
        Bold() {
            super(Typeface.BOLD);
        }
    }
}
