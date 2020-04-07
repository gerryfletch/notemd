package com.notemd;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.notemd.main.MainActivity;

import org.commonmark.node.SoftLineBreak;

import io.noties.markwon.AbstractMarkwonPlugin;
import io.noties.markwon.Markwon;
import io.noties.markwon.MarkwonConfiguration;
import io.noties.markwon.MarkwonVisitor;

public class ViewActivity extends AppCompatActivity {

    private Intent returnToMain;
    private Markwon markwon;
    private Note note;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        returnToMain = new Intent(ViewActivity.this, MainActivity.class);

        markwon = Markwon.builder(this)
                .usePlugin(new AbstractMarkwonPlugin() {
                    @Override
                    public void configureVisitor(@NonNull MarkwonVisitor.Builder builder) {
                        builder.on(SoftLineBreak.class, (visitor, softLineBreak) -> visitor.forceNewLine());
                    }
                }).build();

        note = (Note) getIntent().getSerializableExtra("note");
        if (note == null || note.getNoteId() <= 0) {
            startActivity(returnToMain);
        } else {
            displayMarkdown(note);
            setupBackButton();
            setupEditButton();
        }
    }

    private void displayMarkdown(Note note) {
        TextView displayText = findViewById(R.id.viewText);
        System.out.println(note.getNote());
        markwon.setMarkdown(displayText, note.getNote());
    }

    private void setupBackButton() {
        findViewById(R.id.backButton).setOnClickListener(l -> startActivity(returnToMain));
    }

    private void setupEditButton() {
        findViewById(R.id.editButton).setOnClickListener(l -> startActivity(createWriteIntent()));
        findViewById(R.id.viewButton).setOnClickListener(l -> startActivity(createWriteIntent()));
    }

    private Intent createWriteIntent() {
        Intent goToEdit = new Intent(ViewActivity.this, WriteActivity.class);
        goToEdit.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        goToEdit.putExtra("noteId", note.getNoteId());
        return goToEdit;
    }
}
